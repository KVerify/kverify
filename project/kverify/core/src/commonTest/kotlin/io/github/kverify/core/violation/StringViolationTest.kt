package io.github.kverify.core.violation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StringViolationTest :
    FunSpec({
        val message = "test"

        test("constructor") {
            val violation = StringViolation(message)

            violation.message shouldBe message
        }

        test("asViolation") {
            StringViolation(message) shouldBe message.asViolation()
        }
    })
