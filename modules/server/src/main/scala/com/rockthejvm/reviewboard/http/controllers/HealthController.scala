package com.rockthejvm.reviewboard.http.controllers

import com.rockthejvm.reviewboard.http.endpoints.HealthEndPoints
import zio.*
import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server

class HealthController private extends HealthEndPoints {

  val health =
    healthEndpoint
      .serverLogicSuccess[Task](_ => ZIO.succeed("Hello Esteban from  ZIO"))

}

object HealthController {
  val makeZIO = ZIO.succeed(new HealthController)
}
