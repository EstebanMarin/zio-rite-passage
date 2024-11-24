package com.rockthejvm.reviewboard.http.controllers

import com.rockthejvm.reviewboard.domain.data.*
import com.rockthejvm.reviewboard.http.endpoints.CompanyEndpoints
import org.scalafmt.config.Indents.RelativeToLhs.`match`
import sttp.tapir.server.ServerEndpoint
import zio.*

import collection.mutable

class CompanyControllers private extends BaseController with CompanyEndpoints {
  // TODO implementation DB
  // in memory
  val db = mutable.Map[Long, Company]()
  val create =
    companyEndpoint
      .serverLogicSuccess[Task](request => {
        ZIO.succeed {
          val id         = db.keys.maxOption.getOrElse(0L) + 1
          val slug       = Company.makeSlug(request.name)
          val newCompany = request.toCompany(id)
          db.put(id, newCompany)
          newCompany
        }

      })

  val getAll: ServerEndpoint[Any, Task] =
    getAllEndpoint
      .serverLogicSuccess[Task](_ => ZIO.succeed(db.values.toList))

  val getById =
    getByIdEndpoint
      .serverLogic[Task](id => ZIO.attempt(db.values.find(_.id == id.toLong) match {
        case Some(company) => Right(company)
        case None => Left(("Not Found", "Company not found"))
      }))
  
  val deleteById =
    deleteByIdEndpoint
      .serverLogic[Task](id =>
        ZIO.attempt(db.remove(id.toLong))
          .map {
            case Some(_) => Right("Company deleted")
            case None => Left(("Not Found", "Company not found"))
          }
      )
  val routes = List(create, getAll, getById, deleteById)
}

object CompanyControllers {
  val makeZIO = ZIO.succeed(new CompanyControllers)
}
