package com.rockthejvm.reviewboard

import cats.MonadError
import com.rockthejvm.reviewboard.domain.data.Company
import com.rockthejvm.reviewboard.http.controllers.CompanyControllers
import com.rockthejvm.reviewboard.http.requests.CreateCompanyRequest
import sttp.client3.*
import sttp.client3.testing.SttpBackendStub
import sttp.tapir.Tapir
import sttp.tapir.server.stub.TapirStubInterpreter
import sttp.tapir.ztapir.RIOMonadError
import zio.*
import zio.test.*
import zio.test.Assertion.*

object CompanyControllerSpec extends ZIOSpecDefault {

  private given zioME: RIOMonadError[Any] = new RIOMonadError[Any]
  def spec = suite("CompanyController")(
    test("should create a company successfully") {
      val request = CreateCompanyRequest("Test Company", "test@example.com")
      val createCompany = for {
        controller <- CompanyControllers.makeZIO
        backendStub = SttpBackendStub(zioME)
        interpreter = TapirStubInterpreter(backendStub)
        response <- ZIO.succeed(
          interpreter
            .whenServerEndpoint(controller.create)
            //           .thenRespond(Company(1, "Test Company", "test-company", Some("test@example.com")))
            //   .send(request)
        )
        //   .thenRespond(Company(1, "Test Company", "test-company", Some("test@example.com")))
        //   .send(request)
      } yield response
      // create the controller
      // build the tapir backend
      // run http request
      // check the response

      assertZIO(createCompany)(
        equalTo(Right(Company(1, "Test Company", "test-company", Some("test@example.com"))))
      )
    }
  )

}
