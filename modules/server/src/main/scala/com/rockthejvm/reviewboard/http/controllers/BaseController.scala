package com.rockthejvm.reviewboard.http.controllers

import sttp.tapir.server.ServerEndpoint
import zio.*
import zio.http.Server

trait BaseController {

  val routes: List[ServerEndpoint[Any, Task]]
}
