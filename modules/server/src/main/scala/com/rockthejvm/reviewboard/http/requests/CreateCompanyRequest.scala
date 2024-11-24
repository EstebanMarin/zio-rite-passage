package com.rockthejvm.reviewboard.http.requests

import com.rockthejvm.reviewboard.domain.data.Company
import zio.json.*

final case class CreateCompanyRequest(
    name: String,
    url: String,
    location: Option[String] = None,
    country: Option[String] = None,
    image: Option[String] = None,
    tags: Option[List[String]] = None
) {
  def toCompany(id: Long): Company =
    Company(id, name, url, location, country, image, tags.getOrElse(List()))
}

object CreateCompanyRequest {
  given codec: JsonCodec[CreateCompanyRequest] =
    DeriveJsonCodec.gen[CreateCompanyRequest]
}
