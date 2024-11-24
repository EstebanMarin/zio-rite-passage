package com.rockthejvm.reviewboard.domain.data

import zio.json.*

final case class Company(
    id: Long,
    name: String,
    url: String,
    location: Option[String] = None,
    country: Option[String] = None,
    image: Option[String] = None,
    tags: List[String] = List()
)

object Company {
  given codec: JsonCodec[Company] =
    DeriveJsonCodec.gen[Company]
  def makeSlug(name: String): String =
    name
      .replaceAll(" +", " ")
      .split(" ")
      .map(_.toLowerCase())
      .mkString("-")
}
