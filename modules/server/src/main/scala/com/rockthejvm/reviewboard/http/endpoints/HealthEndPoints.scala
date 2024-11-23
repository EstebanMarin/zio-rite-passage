package com.rockthejvm.reviewboard.http.endpoints

import sttp.tapir.*
import sttp.tapir.server.ziohttp.*
import zio.*
import zio.http.Server

trait HealthEndPoints {
  val healthEndpoint =
    endpoint
      .tag("health")
      .name("health")
      .description("Simple health check")
      .get
      .in("health")
      .out(plainBody[String])

}
