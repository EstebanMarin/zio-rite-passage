package com.rockthejvm.reviewboard

import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server

object Application extends ZIOAppDefault {
  override def run =
    val healthEndpoint =
      endpoint
        .tag("health")
        .name("health")
        .description("Simple health check")
        .get
        .in("health")
        .out(plainBody[String])
        .serverLogicSuccess[Task] { _ =>
          ZIO.succeed("OK")
        }

    val serverProgram = Server.serve(
      ZioHttpInterpreter(ZioHttpServerOptions.default)
        .toHttp(List(healthEndpoint))
    )

    serverProgram.provide(Server.default)
}
