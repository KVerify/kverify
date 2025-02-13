package io.github.kverify.core.exception

import io.github.kverify.core.util.shouldContain
import io.github.kverify.core.violation.AnyViolation
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ValidationExceptionTest :
    FunSpec({
        test("ValidationException creates exception with given violations") {
            val violation1 = AnyViolation("Violation 1")
            val violation2 = AnyViolation("Violation 2")

            val exception = ValidationException(listOf(violation1, violation2))
            val exceptionMessage = exception.message!!

            exceptionMessage shouldContain "Validation failed:"
            exceptionMessage shouldContain "Violation 1"
            exceptionMessage shouldContain "Violation 2"
            exception.violations shouldBe listOf(violation1, violation2)
        }

        test("ValidationException uses default message if violations list is empty") {
            val exception = ValidationException(emptyList<Violation>())

            exception.message shouldBe "Validation failed"
            exception.violations shouldBe emptyList()
        }

        test("ValidationException includes the cause if provided") {
            val cause = Throwable("Some cause")
            val violation = AnyViolation("Violation with cause")

            val exception = ValidationException(listOf(violation), cause = cause)
            val exceptionMessage = exception.message!!

            exception.cause shouldBe cause
            exceptionMessage shouldContain "Validation failed:"
        }

        test("ValidationException handles vararg violations") {
            val violation1 = AnyViolation("Violation 1")
            val violation2 = AnyViolation("Violation 2")

            val exception = ValidationException(violation1, violation2)
            val exceptionMessage = exception.message!!

            exceptionMessage shouldContain "Validation failed:"
            exceptionMessage shouldContain "Violation 1"
            exceptionMessage shouldContain "Violation 2"
            exception.violations shouldBe listOf(violation1, violation2)
        }

        test("ValidationException handles violation messages") {
            val message = "Validation failed"
            val violationMessages = listOf("Violation message 1", "Violation message 2")

            val exception = ValidationException(message = message, violationMessages = violationMessages)

            exception.message shouldBe message
            exception.violations.size shouldBe 2
            exception.violations shouldBe violationMessages.map { it.asViolation() }
        }

        test("ValidationException creates exception from violation messages") {
            val violationMessages = listOf("Message 1", "Message 2")

            val exception = ValidationException(violationMessages = violationMessages)
            val exceptionMessage = exception.message!!

            exceptionMessage shouldContain "Validation failed:"
            exceptionMessage shouldContain "Message 1"
            exceptionMessage shouldContain "Message 2"
            exception.violations.size shouldBe 2
        }

        test("ValidationException can be thrown") {
            val violation = AnyViolation("Sample Violation")

            val exception =
                shouldThrow<ValidationException> {
                    throw ValidationException(listOf(violation))
                }
            val exceptionMessage = exception.message!!

            exceptionMessage shouldContain "Validation failed:"
            exception.violations shouldBe listOf(violation)
        }
    })
