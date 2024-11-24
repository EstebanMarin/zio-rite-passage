package com.rockthejvm.reviewboard.http.controllers

import com.rockthejvm.reviewboard.domain.data.*
import com.rockthejvm.reviewboard.http.endpoints.CompanyEndpoints
import zio.*

import collection.mutable

class CompanyControllers private extends CompanyEndpoints {
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

  val getAll =
    getAllEndpoint
      .serverLogicSuccess[Task](_ => ZIO.succeed(db.values.toList))

  val getById =
    getByIdEndpoint
      .serverLogicSuccess[Task](id => ZIO.attempt(db.values.find(_.id == id.toLong).get))

}

object CompanyControllers {
  val makeZIO = ZIO.succeed(new CompanyControllers)
}
