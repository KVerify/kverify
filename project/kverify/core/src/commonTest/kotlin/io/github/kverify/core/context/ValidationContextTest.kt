package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.assertStoredWithOrder
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
    fun `ValidationContext is functional interface`() {
        ValidationContext {
            @Suppress("USELESS_IS_CHECK")
            if (it !is Violation) fail("Expected violation, but got ${it::class}")
        }.onFailure(violation("error"))
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

        assertRunContextAndReturnsUnchanged(violation) { it applyRule rule }
    }

    @Test
    fun `runRules executes all rules and returns value unchanged`() =
        assertRunContextAndReturnsUnchanged {
            runRules(it, rules.iterator())
        }

    @Test
    fun `applyRules vararg executes all rules and returns value unchanged`() =
        assertRunContextAndReturnsUnchanged {
            "test".applyRules(*rules.toTypedArray())
        }

    @Test
    fun `applyRules iterable executes all rules and returns value unchanged`() =
        assertRunContextAndReturnsUnchanged {
            val rules: Iterable<Rule<String>> = rules
            it applyRules rules
        }

    @Test
    fun `applyRuleUsing applies rule with given context and returns value unchanged`() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        assertRunContextAndReturnsUnchanged(violation) {
            it.applyRuleUsing(this, rule)
        }
    }

    @Test
    fun `applyRulesUsing vararg applies rules with given context`() =
        assertRunContextAndReturnsUnchanged {
            it.applyRulesUsing(this, *rules.toTypedArray())
        }

    @Test
    fun `applyRulesUsing iterable applies rules with given context`() =
        assertRunContextAndReturnsUnchanged {
            val rules: Iterable<Rule<String>> = rules
            it.applyRulesUsing(this, rules)
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

        assertEquals(7, violationStorage.size)
    }

    @Test
    fun `runUnitRules iterable executes rules on Unit`() =
        assertRunContext {
            val rules = violations.toFailingRules<Unit>()

            runUnitRules(rules)
        }

    @Test
    fun `runUnitRules vararg executes rules on Unit`() =
        assertRunContext {
            val rules = violations.toFailingRules<Unit>().toTypedArray()

            runUnitRules(*rules)
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

        assertFalse(evaluated)

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

        assertStoredWithOrder(violations, violationStorage)
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
        assertStoredWithOrder(violations, violationStorage)
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
