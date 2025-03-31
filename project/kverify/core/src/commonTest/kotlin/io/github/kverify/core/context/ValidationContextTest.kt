package io.github.kverify.core.context

import io.github.kverify.core.util.StubRule
import io.github.kverify.core.violation.StringViolation
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class ValidationContextTest :
    FunSpec({
        val failingContext = ValidationContext { fail(it.reason) }
        val message = "test"
        val violation = StringViolation(message)

        val successfulRule = StubRule<Unit>(shouldFail = false, StringViolation("Rule 1 should not fail"))
        val failingRule = StubRule<Unit>(shouldFail = true, StringViolation("Rule 2 should fail"))

        test("onFailure") {
            shouldFail {
                failingContext.onFailure(violation)
            }.message shouldBe violation.reason

            shouldFail {
                failingContext.onFailure(message)
            }.message shouldBe message
        }

        test("applyRules") {
            shouldFail {
                failingContext.run { Unit.applyRules(successfulRule, failingRule) }
            }.message shouldBe failingRule.violation.reason

            shouldFail {
                Unit.applyRules(failingContext, successfulRule, failingRule)
            }.message shouldBe failingRule.violation.reason

            shouldFail {
                failingContext.applyUnitRules(successfulRule, failingRule)
            }.message shouldBe failingRule.violation.reason
        }

        test("validate") {
            shouldFail {
                failingContext.run {
                    validate(!successfulRule.shouldFail) { successfulRule.violation }
                    validate(!failingRule.shouldFail) { failingRule.violation }
                }
            }.message shouldBe failingRule.violation.reason
        }
    })
