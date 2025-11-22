package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.assertStoredWithOrder
import io.github.kverify.core.util.toFailingRules
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.core.violation.asViolationReason
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ValidationContextTest {
    private lateinit var violationStorage: MutableList<Violation>
    private lateinit var context: ValidationContext

    @BeforeTest
    fun setUp() {
        violationStorage = mutableListOf()
        context = ValidationContext { violationStorage.add(it) }
    }

    @Test
    fun `ValidationContext is functional interface`() {
        ValidationContext {
            @Suppress("USELESS_IS_CHECK")
            if (it !is Violation) fail("Expected violation, but got ${it::class}")
        }
    }

    @Test
    fun `ValidationContext all methods are overridable`() {
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
    fun `ValidationContext factory creates context`() {
        var capturedViolation: Violation? = null
        val context = ValidationContext { capturedViolation = it }
        val violation = ViolationReason("test")
        context.onFailure(violation)

        assertEquals(violation, capturedViolation)
    }

    @Test
    fun `applyRule executes rule and returns value`() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = with(context) { "test".applyRule(rule) }

        assertEquals("test", result)
        assertStoredWithOrder(listOf(violation), violationStorage)
    }

    @Test
    fun `runRules executes all rules and returns value unchanged`() {
        val violations = violations("error1", "error2", "error3")
        val rules = violations.toFailingRules<String>()

        val value = "test"
        val result = context.runRules(value, rules.iterator())

        assertEquals(value, result)
        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `applyRules vararg executes all rules and returns value unchanged`() {
        val violations = violations("error1", "error2")
        val rules = violations.toFailingRules<String>().toTypedArray()

        val result =
            with(context) {
                "test".applyRules(*rules)
            }

        assertEquals("test", result)
        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `applyRules iterable executes all rules`() {
        val violations = violations("error1", "error2")
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = with(context) { "test".applyRules(rules) }

        assertEquals("test", result)
        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `applyRuleUsing applies rule with given context`() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".applyRuleUsing(context, rule)

        assertEquals("test", result)
        assertStoredWithOrder(listOf(violation), violationStorage)
    }

    @Test
    fun `applyRulesUsing vararg applies rules with given context`() {
        val violations = violations("error1", "error2")
        val rules = violations.toFailingRules<String>().toTypedArray()

        val result = "test".applyRulesUsing(context, *rules)

        assertEquals("test", result)
        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `applyRulesUsing iterable applies rules with given context`() {
        val violations = violations("error1", "error2")
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test".applyRulesUsing(context, rules)

        assertEquals("test", result)
        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `applyRule variations work with any type`() {
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

        assertEquals(3, violationStorage.size)
    }

    @Test
    fun `runUnitRules iterable executes rules on Unit`() {
        val violations = violations("error1", "error2")
        val rules: Iterable<Rule<Unit>> = violations.toFailingRules()

        context.runUnitRules(rules)

        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `runUnitRules vararg executes rules on Unit`() {
        val violations = violations("error1", "error2")
        val rules = violations.toFailingRules<Unit>().toTypedArray()

        context.runUnitRules(*rules)

        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `failIf calls onFailure when condition is true`() {
        val violation = violation("error")
        context.failIf(true) { violation }

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.first())
    }

    @Test
    fun `failIf does not call onFailure when condition is false`() {
        context.failIf(false) { violation("error") }

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun `failIfNot calls onFailure when condition is false`() {
        val violation = violation("error")
        context.failIfNot(false) { violation }

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.first())
    }

    @Test
    fun `failIfNot does not call onFailure when condition is true`() {
        context.failIfNot(true) { violation("error") }

        assertTrue(violationStorage.isEmpty())
    }

    @Test
    fun `failIf lazy violation is only evaluated when condition is true`() {
        var evaluated = false
        val context = ValidationContext { }

        val violation = violation("error")

        context.failIf(false) {
            evaluated = true
            violation
        }

        assertEquals(false, evaluated)

        context.failIf(true) {
            evaluated = true
            violation
        }

        assertTrue(evaluated)
    }

    @Test
    fun `failIfNot lazy violation is only evaluated when condition is true`() {
        var evaluated = false
        val context = ValidationContext { }

        val violation = violation("error")

        context.failIfNot(false) {
            evaluated = true
            violation
        }

        assertEquals(false, evaluated)

        context.failIfNot(true) {
            evaluated = true
            violation
        }

        assertTrue(evaluated)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class ValidationContextCanBeImplemented : ValidationContext {
    override fun onFailure(violation: Violation): Unit = Unit
}
