package io.github.kverify.core.violation

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StringViolationTest :
    FunSpec({
        val message = "test"

        test("constructor") {
            val violation = ViolationReason(message)

            violation.reason shouldBe message
        }

        test("asViolationReason") {
            ViolationReason(message) shouldBe message.asViolationReason()
        }
    })
