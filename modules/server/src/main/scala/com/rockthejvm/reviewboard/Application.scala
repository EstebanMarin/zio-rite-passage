package com.rockthejvm.reviewboard

import com.rockthejvm.reviewboard.http.HttpApi
import com.rockthejvm.reviewboard.http.controllers.*
import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server

object Application extends ZIOAppDefault {
  override def run =
    val serverProgram = for {
      endpoints <- HttpApi.endpointsZIO
      // controller: HealthController                             <- HealthController.makeZIO
      // controllers: List[HealthController | CompanyControllers] <- HttpApi.makeControllers
      server <-
        Server.serve(
          ZioHttpInterpreter(ZioHttpServerOptions.default).toHttp(endpoints)
        )
      _ <- Console.printLine(s"Server started at http://localhost:8080")
    } yield server

    serverProgram.provide(Server.default)
}
