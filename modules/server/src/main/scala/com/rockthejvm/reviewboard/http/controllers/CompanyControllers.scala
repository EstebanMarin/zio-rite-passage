package com.rockthejvm.reviewboard.http.controllers

import com.rockthejvm.reviewboard.domain.data.*
import com.rockthejvm.reviewboard.http.endpoints.CompanyEndpoints
import sttp.tapir.server.ServerEndpoint
import zio.*

import collection.mutable

class CompanyControllers private extends BaseController with CompanyEndpoints {
  // TODO implementation DB
  // in memory
  val db = mutable.Map.empty[Long, Company]
  val create =
    companyEndpoint
      .serverLogicSuccess[Task](request => {
        ZIO.succeed {
          val id         = db.size + 1
          val slug       = Company.makeSlug(request.name)
          val newCompany = request.toCompany(id)
          newCompany
        }

      })

  val getAll: ServerEndpoint[Any, Task] {
    type SECURITY_INPUT = Unit; type PRINCIPAL = Unit; type INPUT = Unit; type ERROR_OUTPUT = Unit;
    type OUTPUT         = List[Company]
  } =
    getAllEndpoint
      .serverLogicSuccess[Task](_ => ZIO.succeed(db.values.toList))

  val getById =
    getByIdEndpoint
      .serverLogicSuccess[Task](id => ZIO.attempt(db.values.find(_.id == id.toLong).get))

  val routes = List(create, getAll, getById)
}

object CompanyControllers {
  val makeZIO = ZIO.succeed(new CompanyControllers)
}
