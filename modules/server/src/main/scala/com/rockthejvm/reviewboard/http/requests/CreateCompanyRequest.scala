package com.rockthejvm.reviewboard.http.requests

import zio.json.*

final case class CreateCompanyRequest(
    name: String,
    url: String,
    location: Option[String] = None,
    country: Option[String] = None,
    image: Option[String] = None,
    tags: List[String] = List()
)

object CreateCompanyRequest {
  given codec: JsonCodec[CreateCompanyRequest] =
    DeriveJsonCodec.gen[CreateCompanyRequest]
}
