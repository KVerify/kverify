package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ValidationExceptionTest :
    FunSpec({
        val message = "test"
        val cause = Exception()

        test("ValidationException is throwable") {
            val violations = listOf<Violation>()

            val thrownException =
                shouldThrow<ValidationException> {
                    throw ValidationException(
                        message = message,
                        violations = violations,
                        cause = cause,
                    )
                }

            thrownException.message shouldBe message
            thrownException.violations shouldBe violations
            thrownException.cause shouldBe cause
        }

        test("factory function ValidationException(List<Violation>, Throwable?") {
            val violations =
                listOf<Violation>(
                    ViolationReason(message),
                    ViolationReason(message),
                )

            val emptyViolationsException =
                ValidationException(
                    violations = emptyList(),
                    cause = cause,
                )
            val hasViolationsException =
                ValidationException(
                    violations = violations,
                    cause = cause,
                )

            emptyViolationsException.violations shouldBe emptyList()
            emptyViolationsException.cause shouldBe cause
            emptyViolationsException.message shouldBe "Validation failed"

            hasViolationsException.violations shouldBe violations
            hasViolationsException.cause shouldBe cause
            hasViolationsException.message shouldBe validationExceptionMessageGenerator(violations)
        }

        test("factory function ValidationException(vararg Violation, Throwable?)") {
            val exception =
                ValidationException(
                    ViolationReason(message),
                    ViolationReason(message),
                    cause = cause,
                )

            exception.violations shouldBe
                listOf(
                    ViolationReason(message),
                    ViolationReason(message),
                )
            exception.cause shouldBe cause
            exception.message shouldBe
                validationExceptionMessageGenerator(
                    exception.violations,
                )
        }

        test("factory function ValidationException(String?, List<String>, Throwable?)") {
            val exception =
                ValidationException(
                    message,
                    listOf(message, message),
                    cause = cause,
                )

            exception.violations shouldBe
                listOf(
                    ViolationReason(message),
                    ViolationReason(message),
                )
            exception.cause shouldBe cause
            exception.message shouldBe message
        }

        test("factory function ValidationException(List<String>, Throwable?)") {
            val exception =
                ValidationException(
                    listOf(message, message),
                    cause = cause,
                )

            exception.violations shouldBe
                listOf(
                    ViolationReason(message),
                    ViolationReason(message),
                )
            exception.cause shouldBe cause
            exception.message shouldBe
                validationExceptionMessageGenerator(
                    exception.violations,
                )
        }
    })

private fun validationExceptionMessageGenerator(violations: List<Violation>): String =
    "Validation failed: \n${
        violations.joinToString("\n") {
            "\t- ${it.reason}"
        }
    }"
