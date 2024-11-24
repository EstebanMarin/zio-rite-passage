package com.rockthejvm.reviewboard.http.endpoints

import com.rockthejvm.reviewboard.domain.data.*
import com.rockthejvm.reviewboard.http.requests.*
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*

trait CompanyEndpoints {
  val companyEndpoint =
    endpoint
      .tag("company")
      .name("company")
      .description("Create a listing for a company")
      .in("companies")
      .post
      .in(jsonBody[CreateCompanyRequest])
      .out(jsonBody[Company])

  val getAllEndpoint =
    endpoint
      .tag("company")
      .name("getAll")
      .description("Get all companies")
      .in("companies")
      .get
      .out(jsonBody[List[Company]])

  val getByIdEndpoint =
    endpoint
      .tag("company")
      .name("getById")
      .description("Get a company by ID")
      .in("companies" / path[String]("id"))
      .get
      .out(jsonBody[Company])
  
  val deleteByIdEndpoint =
    endpoint
      .tag("company")
      .name("deleteById")
      .description("Delete a company by ID")
      .in("companies" / path[String]("id"))
      .delete
      .out(plainBody[String])

}
