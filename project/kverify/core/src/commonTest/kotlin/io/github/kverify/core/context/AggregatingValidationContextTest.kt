package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.assertStoredWithOrder
import io.github.kverify.core.util.toFailingRules
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AggregatingValidationContextTest {
    private lateinit var violationStorage: MutableList<Violation>
    private lateinit var context: AggregatingValidationContext

    val violations = violations("error1", "error2", "error3")
    val rules = violations.toFailingRules<String>()

    @BeforeTest
    fun setUp() {
        violationStorage = mutableListOf()
        context = AggregatingValidationContext(violationStorage)
    }

    @Test
    fun `onFailure adds violation to storage`() {
        val violation = violation("error")
        context.onFailure(violation)

        assertEquals(1, violationStorage.size)
        assertEquals(listOf(violation), violationStorage.toList())
    }

    @Test
    fun `onFailure adds multiple violations to storage`() {
        violations.forEach(context::onFailure)

        assertStoredWithOrder(violations, violationStorage)
    }

    @Test
    fun `build returns Valid when no violations`() {
        val result = context.build()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun `build returns Invalid when violations exist`() {
        violations.forEach(context::onFailure)

        val result = context.build()

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAll returns Valid when no violations`() {
        val result = validateAll { /* No violations */ }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun `validateAll returns Invalid when violations exist`() {
        val violation = violation("error")
        val result = validateAll { onFailure(violation) }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `validateAll executes rules and collects violations`() {
        val result = validateAll { "test" applyRules rules }

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `runValidatingAll returns success when no violations`() {
        val value = "test value"
        val result = runValidatingAll { value }

        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrNull())
    }

    @Test
    fun `runValidatingAll returns failure when violations exist`() {
        val violation = violation("error")
        val result =
            runValidatingAll {
                onFailure(violation)
                "test value"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertEquals(violation, exception.violations.first())
    }

    @Test
    fun `validateAllWithRule validates single rule`() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" validateAllWithRule rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `validateAllWithRules with iterator validates all rules`() {
        val result = "test" validateAllWithRules rules.iterator()

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAllWithRules with iterable validates all rules`() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" validateAllWithRules rules

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAllWithRules with no arguments returns Valid`() {
        val result = "test".validateAllWithRules()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun `validateAllWithRules with single rule validates rule`() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllWithRules(rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `validateAllWithRules with vararg validates all rules`() {
        val result = "test".validateAllWithRules(*rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAllTo uses provided destination`() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")

        val result =
            validateAllTo(destination) {
                onFailure(violation)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), destination.toList())
    }

    @Test
    fun `runValidatingAllTo uses provided destination`() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")

        val result =
            runValidatingAllTo(destination) {
                onFailure(violation)
                42
            }

        assertTrue(result.isFailure)
        assertEquals(listOf(violation), destination.toList())
    }

    @Test
    fun `validateAllWithRuleTo uses provided destination`() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllWithRuleTo(destination, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), destination.toList())
    }

    @Test
    fun `validateAllWithRulesTo with iterator uses provided destination`() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllWithRulesTo(destination, rules.iterator())

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, destination)
    }

    @Test
    fun `validateAllWithRulesTo with iterable uses provided destination`() {
        val destination = mutableListOf<Violation>()
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test".validateAllWithRulesTo(destination, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, destination)
    }

    @Test
    fun `validateAllWithRulesTo with no rules uses provided destination`() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllWithRulesTo(destination)

        assertIs<ValidationResult.Valid>(result)
        assertTrue(destination.isEmpty())
    }

    @Test
    fun `validateAllWithRulesTo with single rule uses provided destination`() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllWithRulesTo(destination, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), destination.toList())
    }

    @Test
    fun `validateAllWithRulesTo with vararg uses provided destination`() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllWithRulesTo(destination, *rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, destination)
    }

    @Test
    fun `validateAllUsing uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")

        val result =
            validateAllUsing(customContext) {
                onFailure(violation)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `runValidatingAllUsing uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")

        val result =
            runValidatingAllUsing(customContext) {
                onFailure(violation)
                "result"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
    }

    @Test
    fun `validateAllWithRuleUsing uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllWithRuleUsing(customContext, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `validateAllWithRulesUsing with iterator uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllWithRulesUsing(customContext, rules.iterator())

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAllWithRulesUsing with iterable uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test".validateAllWithRulesUsing(customContext, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `validateAllWithRulesUsing with no rules returns Valid`() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllWithRulesUsing(customContext)

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun `validateAllWithRulesUsing with single rule uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllWithRulesUsing(customContext, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }

    @Test
    fun `validateAllWithRulesUsing with vararg uses provided context`() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllWithRulesUsing(customContext, *rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertStoredWithOrder(violations, result.violations)
    }

    @Test
    fun `AggregatingValidationContext can be extended`() {
        val customContext =
            object : AggregatingValidationContext(mutableListOf()) {
                override fun build(): ValidationResult = super.build()
            }

        val violation = violation("error")
        customContext.onFailure(violation)

        val result = customContext.build()
        assertIs<ValidationResult.Invalid>(result)
    }

    @Test
    fun `validateAll works with any type including null`() {
        val value: Any? = null
        val violation = violation("error")
        val rule = FailingRule<Any?>(violation)

        val result =
            validateAll {
                value.applyRule(rule)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(listOf(violation), result.violations)
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class AggregatingValidationContextCanBeExtended(
    violationsStorage: MutableCollection<Violation>,
) : AggregatingValidationContext(violationsStorage) {
    override fun build(): ValidationResult = TODO()
}
