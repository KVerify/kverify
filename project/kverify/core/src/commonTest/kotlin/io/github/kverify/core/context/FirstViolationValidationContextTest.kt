package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.violation
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class FirstViolationValidationContextTest {
    private val violation = violation("error")
    private val rule = FailingRule<String>(violation)

    private val violationList = violations("error1", "error2")
    private val failingRules = violationList.map { FailingRule<String>(it) }

    @Test
    fun verifyFirstWithNoViolations() {
        val result =
            verifyFirst {
                "test" verifyWith PassingRule
            }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun verifyFirstWithSingleViolation() {
        val result =
            verifyFirst {
                "test" verifyWith rule
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun verifyFirstStopsAfterFirstViolation() {
        var secondRuleExecuted = false

        val result =
            verifyFirst {
                "test" verifyWith failingRules[0]
                secondRuleExecuted = true
                "test" verifyWith failingRules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
        assertTrue(secondRuleExecuted)
    }

    @Test
    fun verifyFirstWithMultipleRulesStopsAtFirst() {
        val result =
            verifyFirst {
                "test" verifyWith failingRules
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
    }

    @Test
    fun valueVerifyWithFirstNoRules() {
        val result = "test".verifyWithFirst()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun valueVerifyWithFirstSingleRule() {
        val result = "test".verifyWithFirst(rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun valueVerifyWithFirstVarargRules() {
        val result = "test".verifyWithFirst(failingRules[0], failingRules[1])

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
    }

    @Test
    fun valueVerifyWithFirstIterableRules() {
        val result = "test".verifyWithFirst(failingRules)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList[0], result.violations.single())
    }

    @Test
    fun passesWithNoRules() {
        val result = "test".passes()

        assertTrue(result)
    }

    @Test
    fun passesWithPassingRule() {
        val result = "test".passes(PassingRule)

        assertTrue(result)
    }

    @Test
    fun passesWithFailingRule() {
        val result = "test" passes rule

        assertFalse(result)
    }

    @Test
    fun passesWithVarargRules() {
        val result = "test".passes(PassingRule, rule)

        assertFalse(result)
    }

    @Test
    fun passesWithIterableRules() {
        val rules =
            listOf(
                PassingRule as io.github.kverify.core.rule.Rule<String>,
                FailingRule(violation),
            )

        val result = "test" passes rules

        assertFalse(result)
    }

    @Test
    fun passesWithAllPassingRules() {
        val rules =
            listOf(
                PassingRule as io.github.kverify.core.rule.Rule<String>,
                PassingRule,
                PassingRule,
            )

        val result = "test" passes rules

        assertTrue(result)
    }

    @Test
    fun notPassesWithNoRules() {
        val result = "test".notPasses()

        assertFalse(result)
    }

    @Test
    fun notPassesWithPassingRule() {
        val result = "test" notPasses PassingRule

        assertFalse(result)
    }

    @Test
    fun notPassesWithFailingRule() {
        val result = "test" notPasses rule

        assertTrue(result)
    }

    @Test
    fun notPassesWithVarargRules() {
        val result = "test".notPasses(PassingRule, rule)

        assertTrue(result)
    }

    @Test
    fun notPassesWithIterableRules() {
        val rules =
            listOf(
                PassingRule as io.github.kverify.core.rule.Rule<String>,
                FailingRule(violation),
            )

        val result = "test" notPasses rules

        assertTrue(result)
    }

    @Test
    fun verifyFirstWithPassingThenFailingRule() {
        val result =
            verifyFirst {
                "test" verifyWith PassingRule
                "test" verifyWith rule
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun verifyFirstDoesNotExecuteRulesAfterFirstViolation() {
        val executionOrder = mutableListOf<String>()

        verifyFirst {
            executionOrder.add("before")
            val rules =
                listOf(
                    PassingRule as io.github.kverify.core.rule.Rule<String>,
                    failingRules[0],
                    failingRules[1],
                )
            "test" verifyWith rules
            executionOrder.add("after")
        }

        assertEquals(
            listOf("before", "after"),
            executionOrder,
        )
    }
}
