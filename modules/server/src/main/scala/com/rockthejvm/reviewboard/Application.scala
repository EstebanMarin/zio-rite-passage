package com.rockthejvm.reviewboard

import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server
import com.rockthejvm.reviewboard.http.controllers.*
import com.rockthejvm.reviewboard.http.HttpApi

object Application extends ZIOAppDefault {
  override def run =
    val serverProgram = for {
      controller: HealthController                             <- HealthController.makeZIO
      controllers: List[HealthController | CompanyControllers] <- HttpApi.makeControllers
      server <-
        Server.serve(
          ZioHttpInterpreter(ZioHttpServerOptions.default)
            .toHttp(List(controller.health))
        )
      _ <- Console.printLine(s"Server started at http://localhost:8080")
    } yield server

    serverProgram.provide(Server.default)
}
