package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.applyRules
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import io.kotest.assertions.shouldFail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.test.fail

class RuleTest :
    FunSpec({
        val failContext = ValidationContext { fail(it.message) }

        test("Rule fun interface creation") {
            val message = "Failure"
            val rule =
                Rule<String> { string ->
                    onFailure(string.asViolation())
                }

            shouldFail {
                message.applyRules(failContext, rule)
            }.message shouldBe message
        }

        test("Rule(predicate, violationGenerator) factory function") {
            val message = "Failure"

            val passingRule =
                Rule<String>(
                    { true },
                    {
                        fail("violationShould not be called if the predicate returns true")
                    },
                )
            val failingRule =
                Rule<String>(
                    { false },
                    { message.asViolation() },
                )

            message.applyRules(failContext, passingRule)
            shouldFail {
                message.applyRules(failContext, failingRule)
            }.message shouldBe message
        }

        test("Rule.plus") {
            val collectedViolations = mutableListOf<Violation>()
            val collectingContext = ValidationContext { collectedViolations.add(it) }

            val violation1 = "1".asViolation()
            val violation2 = "2".asViolation()

            val rule1 = Rule<String> { onFailure(violation1) }
            val rule2 = Rule<String> { onFailure(violation2) }
            val ruleSum = rule1 + rule2

            "".applyRules(collectingContext, ruleSum)

            collectedViolations[0] shouldBe violation1
            collectedViolations[1] shouldBe violation2
        }
    })
