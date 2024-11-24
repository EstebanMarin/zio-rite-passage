package com.rockthejvm.reviewboard.http.requests

import zio.json.*
import com.rockthejvm.reviewboard.domain.data.Company

final case class CreateCompanyRequest(
    name: String,
    url: String,
    location: Option[String] = None,
    country: Option[String] = None,
    image: Option[String] = None,
    tags: List[String] = List()
) {
  def toCompany(id: Long): Company =
    Company(id, name, url, location, country, image, tags)
}

object CreateCompanyRequest {
  given codec: JsonCodec[CreateCompanyRequest] =
    DeriveJsonCodec.gen[CreateCompanyRequest]
}
