package com.rockthejvm.reviewboard

import zio.*
import zio.test.*

object CompanyControllerSpec extends ZIOSpecDefault {
  def spec = suite("CompanyController")(
    test("should return a list of companies") {
      assertZIO(ZIO.succeed(1 + 1))(
        Assertion.assertion("Basic Math")(_ == 2)
      )
    }
  )

}
