package com.rockthejvm.reviewboard

import zio.*

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

    override def run =
      serverProgram.provide(Server.default)
      }
}
