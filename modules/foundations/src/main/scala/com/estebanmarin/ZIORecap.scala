package com.estebanmarin

import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server
import zio.json.*

object ZIORecap extends ZIOAppDefault {
  val simpleHealthEndpoint =
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
      .toHttp(List(simpleHealthEndpoint))
  )

  override def run =
    Console.printLine("Hello, world!")
}
