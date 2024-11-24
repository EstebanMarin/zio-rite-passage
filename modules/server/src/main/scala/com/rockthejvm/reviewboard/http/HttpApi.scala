package com.rockthejvm.reviewboard.http

import com.rockthejvm.reviewboard.http.controllers.*
import sttp.tapir.server.*
import zio.*

object HttpApi {
  def gatherRoutes(controllers: List[BaseController]): List[ServerEndpoint[Any, Task]] =
    controllers.flatMap(_.routes)
  def makeControllers: Task[List[HealthController | CompanyControllers]] = for {
    healthController  <- HealthController.makeZIO
    companyController <- CompanyControllers.makeZIO
    // add new controllers here
  } yield List(healthController, companyController)
  def endpointsZIO = makeControllers.map(gatherRoutes)
}
