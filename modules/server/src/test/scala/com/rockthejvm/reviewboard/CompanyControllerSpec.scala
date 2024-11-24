package com.rockthejvm.reviewboard

import cats.MonadError
import com.rockthejvm.reviewboard.domain.data.Company
import com.rockthejvm.reviewboard.http.controllers.CompanyControllers
import com.rockthejvm.reviewboard.http.requests.CreateCompanyRequest
import sttp.client3.*
import sttp.client3.testing.SttpBackendStub
import sttp.tapir.Tapir
import sttp.tapir.generic.auto.*
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.tapir.ztapir.RIOMonadError
import zio.*
import zio.json.*
import zio.test.*
import zio.test.Assertion.*

object CompanyControllerSpec extends ZIOSpecDefault {

  private given zioME: RIOMonadError[Any] = new RIOMonadError[Any]

  private def backendStubZIO(endpoint: CompanyControllers => ServerEndpoint[Any, Task]) = for {
    controller <- CompanyControllers.makeZIO
    backendStub <- ZIO.succeed(
      TapirStubInterpreter(SttpBackendStub(zioME))
        .whenServerEndpointRunLogic(endpoint(controller))
        .backend()
    )
  } yield backendStub
  def spec = suite("CompanyController")(
    test("should create a company successfully") {
      val request = CreateCompanyRequest("Test Company", "test.com")
      val program = for {
        backendStub <- backendStubZIO(_.create)
        response <- basicRequest
          .post(uri"/companies")
          .body(request.toJson)
          .send(backendStub)
      } yield response.body

      assertZIO(program)(
        Assertion.assertion("testing creating company") { responseBody =>
          responseBody.toOption
            .flatMap(_.fromJson[Company].toOption)
            .contains(Company(1, "test-company", "test.com", None, None, None, Nil))
        }
      )
    },
    test("should get all companies") {
      val program = for {
        backendStub <- backendStubZIO(_.create)
        response <- basicRequest
          .get(uri"/companies/1")
          .send(backendStub)
      } yield response.body

      assertZIO(program)(
        Assertion.assertion("testing getting all companies") { responseBody =>
          responseBody.toOption
            .flatMap(_.fromJson[List[Company]].toOption)
            .contains(List())
        }
      )
    },
  )

}
