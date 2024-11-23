package com.rockthejvm.reviewboard.http.controllers

import zio.*
import com.rockthejvm.reviewboard.domain.data.*
import com.rockthejvm.reviewboard.http.endpoints.CompanyEndpoints
import collection.mutable

class CompanyControllers extends CompanyEndpoints {
    // TODO implementation DB
    // in memory
    val companyRepository = mutable.Map.empty[Long, Company]  
    val company =
        companyEndpoint
        .serverLogicSuccess[Task](request => {
            val company = Company(
                ???,
            request.name,
            request.url,
            request.location,
            request.country,
            request.image,
            request.tags
            )
            // companyRepository.create(company)
            ZIO.succeed(company) 
        })

    val getAll =
        getAllEndpoint
        .serverLogicSuccess[Task](_ => companyRepository.getAll())

    val getById =
        getByIdEndpoint
        .serverLogicSuccess[Task](id => companyRepository.getById(id))
  
}
