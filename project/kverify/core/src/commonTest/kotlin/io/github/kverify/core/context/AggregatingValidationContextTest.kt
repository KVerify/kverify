package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.util.StubRule
import io.github.kverify.core.util.shouldContainExactly
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class AggregatingValidationContextTest :
    FunSpec({
        val message = "test"
        val violation = ViolationReason(message)
        val failingRule = StubRule<Any>(shouldFail = true, violation)

        test("onFailure adds violation to storage") {
            val violationsStorage = mutableListOf<Violation>()
            val context = AggregatingValidationContext(violationsStorage)

            context.onFailure(violation)

            violationsStorage.shouldContainExactly(violation)
        }

        test("validateAll stores violations into new ArrayList") {
            val result =
                validateAll {
                    onFailure(violation)
                }

            result.violations.shouldContainExactly(violation)
        }

        test("validateAllTo uses provided destination") {
            val storage = mutableListOf<Violation>()
            val result =
                validateAllTo(storage) {
                    onFailure(violation)
                }

            result.violations.shouldContainExactly(violation)
            storage.shouldContainExactly(violation)
        }

        test("validateAllUsing stores violations into given context") {
            val context = AggregatingValidationContext(mutableListOf())
            val result =
                validateAllUsing(context) {
                    onFailure(violation)
                }

            result.violations.shouldContainExactly(violation)
            context.violationsStorage.shouldContainExactly(violation)
        }

        test("validateAllWithRules applies rules to receiver") {
            val result = Unit.validateAllWithRules(failingRule)

            result.violations.shouldContainExactly(violation)
        }

        test("validateAllWithRulesTo applies rules using provided destination") {
            val storage = mutableListOf<Violation>()
            val result = Unit.validateAllWithRulesTo(storage, failingRule)

            result.violations.shouldContainExactly(violation)
            storage.shouldContainExactly(violation)
        }

        test("validateAllWithRulesUsing applies rules using custom context") {
            val context = AggregatingValidationContext(mutableListOf())
            val result = Unit.validateAllWithRulesUsing(context, failingRule)

            result.violations.shouldContainExactly(violation)
            context.violationsStorage.shouldContainExactly(violation)
        }

        test("runValidatingAll returns success if no violations") {
            val result =
                runValidatingAll {
                    "success"
                }

            result.getOrThrow() shouldBe "success"
        }

        test("runValidatingAllTo returns failure if violations exist") {
            val storage = mutableListOf<Violation>()
            val result =
                runValidatingAllTo(storage) {
                    Unit.applyRules(failingRule)
                    "value"
                }

            shouldThrow<ValidationException> {
                result.getOrThrow()
            }.violations.shouldContainExactly(violation)
            storage.shouldContainExactly(violation)
        }

        test("runValidatingAllUsing respects context violations") {
            val context = AggregatingValidationContext(mutableListOf())
            val result =
                runValidatingAllUsing(context) {
                    onFailure(violation)
                    42
                }

            shouldThrow<ValidationException> {
                result.getOrThrow()
            }.violations.shouldContainExactly(violation)
            context.violationsStorage.shouldContainExactly(violation)
        }
    })
