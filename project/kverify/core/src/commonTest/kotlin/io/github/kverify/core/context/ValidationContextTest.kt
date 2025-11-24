package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.assertContainsAllWithOrder
import io.github.kverify.core.util.toFailingRules
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationContextTest {
    private lateinit var violationStorage: MutableList<Violation>
    private lateinit var context: ValidationContext

    val violations = violations("error1", "error2", "error3")
    val rules = violations.toFailingRules<String>()

    @BeforeTest
    fun setUp() {
        violationStorage = mutableListOf()
        context = ValidationContext { violationStorage.add(it) }
    }

    @Test
    fun validationContextIsFunctionalInterface() {
        ValidationContext {
            @Suppress("USELESS_IS_CHECK")
            if (it !is Violation) fail("Expected violation, but got ${it::class}")
        }.onFailure(violation("error"))
    }

    @Test
    fun validationContextAllMethodsAreOverridable() {
        object : ValidationContext {
            override fun onFailure(violation: Violation): Unit = Unit

            override fun <T> runRules(
                value: T,
                rulesIterator: Iterator<Rule<T>>,
            ): T = value

            override fun <T> T.applyRule(rule: Rule<T>): T = this

            override fun <T> T.applyRules(vararg rules: Rule<T>): T = this

            override fun <T> T.applyRules(rules: Iterable<Rule<T>>): T = this
        }
    }

    @Test
    fun validationContextFactoryCreatesContext() {
        var capturedViolation: Violation? = null
        val context = ValidationContext { capturedViolation = it }
        val violation = ViolationReason("test")
        context.onFailure(violation)

        assertEquals(violation, capturedViolation)
    }

    @Test
    fun applyRuleExecutesRuleAndReturnsValue() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        assertRunContextAndReturnsUnchanged(violation) { it applyRule rule }
    }

    @Test
    fun runRulesExecutesAllRulesAndReturnsValueUnchanged() =
        assertRunContextAndReturnsUnchanged {
            runRules(it, rules.iterator())
        }

    @Test
    fun applyRulesVarargExecutesAllRulesAndReturnsValueUnchanged() =
        assertRunContextAndReturnsUnchanged {
            "test".applyRules(*rules.toTypedArray())
        }

    @Test
    fun applyRulesIterableExecutesAllRulesAndReturnsValueUnchanged() =
        assertRunContextAndReturnsUnchanged {
            val rules: Iterable<Rule<String>> = rules
            it applyRules rules
        }

    @Test
    fun applyRuleUsingAppliesRuleWithGivenContextAndReturnsValueUnchanged() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        assertRunContextAndReturnsUnchanged(violation) {
            it.applyRuleUsing(this, rule)
        }
    }

    @Test
    fun applyRulesUsingVarargAppliesRulesWithGivenContext() =
        assertRunContextAndReturnsUnchanged {
            it.applyRulesUsing(this, *rules.toTypedArray())
        }

    @Test
    fun applyRulesUsingIterableAppliesRulesWithGivenContext() =
        assertRunContextAndReturnsUnchanged {
            val rules: Iterable<Rule<String>> = rules
            it.applyRulesUsing(this, rules)
        }

    @Test
    fun applyRuleVariationsWorkWithAnyType() {
        val value: Any? = null
        val rule = FailingRule<Any?>("error")

        with(context) {
            value.applyRule(rule)
            @Suppress("RemoveRedundantSpreadOperator")
            value.applyRules(*arrayOf(rule))
            value.applyRules(listOf(rule))
            runRules(value, listOf(rule).iterator())
        }

        value.applyRuleUsing(context, rule)
        @Suppress("RemoveRedundantSpreadOperator")
        value.applyRulesUsing(context, *arrayOf(rule))
        value.applyRulesUsing(context, listOf(rule))

        assertEquals(7, violationStorage.size)
    }

    @Test
    fun runUnitRulesIterableExecutesRulesOnUnit() =
        assertRunContext {
            val rules = violations.toFailingRules<Unit>()

            runUnitRules(rules)
        }

    @Test
    fun runUnitRulesVarargExecutesRulesOnUnit() =
        assertRunContext {
            val rules = violations.toFailingRules<Unit>().toTypedArray()

            runUnitRules(*rules)
        }

    @Test
    fun failIfCallsOnFailureWhenConditionIsTrue() {
        val violation = violation("error")
        context.failIf(true) { violation }

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.single())
    }

    @Test
    fun failIfDoesNotCallOnFailureWhenConditionIsFalse() {
        context.failIf(false) { violation("error") }

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun failIfNotCallsOnFailureWhenConditionIsFalse() {
        val violation = violation("error")
        context.failIfNot(false) { violation }

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.single())
    }

    @Test
    fun failIfNotDoesNotCallOnFailureWhenConditionIsTrue() {
        context.failIfNot(true) { violation("error") }

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun failIfLazyViolationIsOnlyEvaluatedWhenConditionIsTrue() {
        var evaluated = false
        val context = ValidationContext { }

        val violation = violation("error")

        context.failIf(false) {
            evaluated = true
            violation
        }

        assertFalse(evaluated)

        context.failIf(true) {
            evaluated = true
            violation
        }

        assertTrue(evaluated)
    }

    @Test
    fun failIfNotLazyViolationIsOnlyEvaluatedWhenConditionIsTrue() {
        var evaluated = false
        val context = ValidationContext { }

        val violation = violation("error")

        context.failIfNot(true) {
            evaluated = true
            violation
        }

        assertFalse(evaluated)

        context.failIfNot(false) {
            evaluated = true
            violation
        }

        assertTrue(evaluated)
    }

    private inline fun assertRunContext(
        violations: List<Violation> = this.violations,
        block: ValidationContext.() -> Unit,
    ) {
        context.block()

        assertContainsAllWithOrder(violations, violationStorage)
    }

    private inline fun assertRunContext(
        vararg violations: Violation,
        block: ValidationContext.() -> Unit,
    ) = assertRunContext(violations.asList(), block)

    private inline fun assertRunContextAndReturnsUnchanged(
        violations: List<Violation> = this.violations,
        block: ValidationContext.(String) -> String,
    ) {
        val value = "test"
        val result = with(context) { block(value) }

        assertEquals(value, result)
        assertContainsAllWithOrder(violations, violationStorage)
    }

    private fun assertRunContextAndReturnsUnchanged(
        vararg violations: Violation,
        block: ValidationContext.(String) -> String,
    ) = assertRunContextAndReturnsUnchanged(violations.asList(), block)
}

@Suppress("UnusedPrivateClass", "Unused")
private class ValidationContextCanBeImplemented : ValidationContext {
    override fun onFailure(violation: Violation): Unit = Unit
}
