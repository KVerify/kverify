package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.util.executionCheck
import io.github.kverify.core.violation.StringViolation
import io.github.kverify.core.violation.Violation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.DefaultAsserter.fail

class ValidationResultTest :
    FunSpec({
        val violation = StringViolation("test")

        test("isValid") {
            ValidationResult(emptyList()).isValid shouldBe true
        }

        test("isInvalid") {
            ValidationResult(
                listOf(violation),
            ).isInvalid shouldBe true
        }

        test("plus(Violation)") {
            val validResult = ValidationResult(emptyList())

            val sumResult = validResult + violation

            sumResult.violations shouldBe listOf(violation)
            sumResult.isInvalid shouldBe true
        }

        test("plus(Collection<Violation>)") {
            val violationCollection: Collection<Violation> = List(3) { violation }
            val validResult = ValidationResult(emptyList())

            val sumResult = validResult + violationCollection

            sumResult.violations shouldBe violationCollection
            sumResult.isInvalid shouldBe true
        }

        test("plus(ValidationResult)") {
            val violationList = List(3) { violation }
            val validResult = ValidationResult(emptyList())
            val otherResult = ValidationResult(violationList)

            val sumResult = validResult + otherResult

            sumResult.violations shouldBe violationList
            sumResult.isInvalid shouldBe true
        }

        test("toString") {
            val violationList = List(11) { violation }
            val validResultString = ValidationResult(emptyList()).toString()
            val otherResultString = ValidationResult(violationList).toString()

            validResultString shouldBe "ValidationResult(valid=true)"
            otherResultString shouldBe "ValidationResult(valid=false, violations=${
                violationList.joinToString(
                    separator = ", ",
                    prefix = "[",
                    postfix = "]",
                    limit = 10,
                )
            })"
        }

        test("Companion.VALID") {
            ValidationResult.Companion.VALID shouldBe ValidationResult(emptyList())
        }

        test("factory function ValidationResult(vararg Violation)") {
            val violationList = List(3) { violation }
            ValidationResult(violations = violationList.toTypedArray()) shouldBe ValidationResult(violationList)
        }

        test("merge") {
            val amount = 3

            val violationList = List(amount) { violation }
            val expectedViolationList = List(amount) { violationList }.flatten()

            val validResult = ValidationResult(emptyList())
            val validationResultList = List(amount) { ValidationResult(violationList) }

            val merged = validResult.merge(validationResultList)
            val mergedResults = validationResultList.mergeResults()

            merged.violations shouldBe expectedViolationList
            mergedResults.violations shouldBe expectedViolationList
        }

        test("onValid") {
            val validResult = ValidationResult(emptyList())
            val invalidResult = ValidationResult(listOf(violation))

            executionCheck("onValid should be called for valid result") {
                validResult.onValid { execute() }
            }

            invalidResult.onValid { fail("onValid should not be called for invalid result") }
        }

        test("onInvalid") {
            val validResult = ValidationResult(emptyList())
            val invalidResult = ValidationResult(listOf(violation))

            validResult.onInvalid { fail("onInvalid should not be called for valid result") }

            executionCheck("onInvalid should be called for invalid result") {
                invalidResult.onInvalid { execute() }
            }
        }

        test("fold") {
            val value = "test"

            val validResult = ValidationResult(emptyList())
            val invalidResult = ValidationResult(listOf(violation))

            validResult.fold(
                onValid = { value },
                onInvalid = { fail("onInvalid should not be called for valid result") },
            ) shouldBe value

            invalidResult.fold(
                onValid = { fail("onValid should not be called for invalid result") },
                onInvalid = { value },
            ) shouldBe value
        }

        test("throwOnFailure") {
            val violationList = List(30) { violation }
            val exception = Exception()

            val validResult = ValidationResult(emptyList())
            val invalidResult = ValidationResult(violationList)

            val expectedMessage =
                violationList.joinToString(
                    separator = ", ",
                    prefix = "Validation failed: [",
                    postfix = "]",
                    limit = 10,
                    truncated = "...",
                    transform = { it.message },
                )

            validResult.throwOnFailure()

            val thrownException =
                shouldThrow<ValidationException> {
                    invalidResult.throwOnFailure(
                        separator = ", ",
                        prefix = "Validation failed: [",
                        postfix = "]",
                        limit = 10,
                        truncated = "...",
                        cause = exception,
                        transform = { it.message },
                    )
                }

            thrownException.message shouldBe expectedMessage
            thrownException.cause shouldBe exception
            thrownException.violations shouldBe violationList
        }
    })
