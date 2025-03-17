package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.util.DummyRule
import io.github.kverify.core.util.shouldContainExactly
import io.github.kverify.core.violation.StringViolation
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.test.assertTrue

class ThrowingValidationContextTest :
    FunSpec({
        val message = "test"
        val violation = StringViolation(message)
        val failingRule = DummyRule<Any>(shouldFail = true, violation)

        test("onFailure") {
            shouldThrow<ValidationException> {
                ThrowingValidationContext.onFailure(violation)
                ThrowingValidationContext.onFailure(violation)
            }.violations.shouldContainExactly(violation)

            shouldThrow<ValidationException> {
                ThrowingValidationContext.onFailure(message)
                ThrowingValidationContext.onFailure(message)
            }.violations.shouldContainExactly(violation)
        }

        test("validate") {
            ThrowingValidationContext.validate(true) { violation }

            shouldThrow<ValidationException> {
                ThrowingValidationContext.validate(false) { violation }
                ThrowingValidationContext.validate(false) { violation }
            }.violations.shouldContainExactly(violation)

            // contract casts
            val notNull: Int? = 1
            ThrowingValidationContext.validate(notNull != null) { violation }
            notNull + 1
        }

        test("validateThatOrThrow") {
            shouldThrow<ValidationException> {
                validateThatOrThrow(false) { violation }
                validateThatOrThrow(false) { violation }
            }.violations.shouldContainExactly(violation)

            // contract casts
            val notNull: Int? = 1
            validateThatOrThrow(notNull != null) { violation }
            notNull + 1
        }

        test("validateOrThrow") {
            shouldThrow<ValidationException> {
                validateOrThrow {
                    onFailure(violation)
                    onFailure(violation)
                }
            }.violations.shouldContainExactly(violation)
        }

        test("validateFirst") {
            validateFirst {
                onFailure(violation)
                onFailure(violation)
            }.violations.shouldContainExactly(violation)
        }

        test("validateOrThrowWithRules") {
            shouldThrow<ValidationException> {
                Unit.validateOrThrowWithRules(
                    failingRule,
                    failingRule,
                )
            }.violations.shouldContainExactly(violation)
        }

        test("validateFirstWithRules") {
            Unit
                .validateFirstWithRules(
                    failingRule,
                    failingRule,
                ).violations
                .shouldContainExactly(violation)
        }

        test("runValidatingFirst") {
            val value = "test"

            val successResult = runValidatingFirst { value }

            val failedResult =
                runValidatingFirst {
                    onFailure(violation)
                    onFailure(violation)

                    value
                }

            assertTrue { successResult.isSuccess }
            assertTrue { failedResult.isFailure }

            successResult.getOrThrow() shouldBe value
            shouldThrow<ValidationException> {
                failedResult.getOrThrow() shouldNotBe value
            }.violations.shouldContainExactly(violation)
        }
    })
