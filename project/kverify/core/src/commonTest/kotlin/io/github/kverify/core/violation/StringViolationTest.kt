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

// Checks if anything breaking was added to the interface
@Suppress("UnusedPrivateClass")
private class TestViolation(
    override val message: String,
) : Violation
