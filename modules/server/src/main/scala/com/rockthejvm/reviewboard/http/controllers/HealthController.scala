package com.rockthejvm.reviewboard.http.controllers

import com.rockthejvm.reviewboard.http.endpoints.HealthEndPoints
import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.*
import zio.http.Server

class HealthController private extends BaseController with HealthEndPoints {

  val health =
    healthEndpoint
      .serverLogicSuccess[Task](_ => ZIO.succeed("Hello Esteban from  ZIO"))

  val routes = List(health)
}

object HealthController {
  val makeZIO = ZIO.succeed(new HealthController)
}
