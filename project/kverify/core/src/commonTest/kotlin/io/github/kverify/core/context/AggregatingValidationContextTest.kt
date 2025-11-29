package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.assertContainsAllWithOrder
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
    fun onFailureAddsViolationToStorage() {
        val violation = violation("error")
        context.onFailure(violation)

        assertEquals(1, violationStorage.size)
        assertEquals(violation, violationStorage.single())
    }

    @Test
    fun onFailureAddsMultipleViolationsToStorage() {
        violations.forEach(context::onFailure)

        assertContainsAllWithOrder(violations, violationStorage)
    }

    @Test
    fun buildReturnsValidWhenNoViolations() {
        val result = context.build()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun buildReturnsInvalidWhenViolationsExist() {
        violations.forEach(context::onFailure)

        val result = context.build()

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllReturnsValidWhenNoViolations() {
        val result = validateAll { /* No violations */ }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateAllReturnsInvalidWhenViolationsExist() {
        val violation = violation("error")
        val result = validateAll { onFailure(violation) }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateAllExecutesRulesAndCollectsViolations() {
        val result = validateAll { "test" applyRules rules }

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun runValidatingAllReturnsSuccessWhenNoViolations() {
        val value = "test value"
        val result = runValidatingAll { value }

        assertTrue(result.isSuccess)
        assertEquals(value, result.getOrNull())
    }

    @Test
    fun runValidatingAllReturnsFailureWhenViolationsExist() {
        val violation = violation("error")
        val result =
            runValidatingAll {
                onFailure(violation)
                "test value"
            }

        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertIs<ValidationException>(exception)
        assertEquals(violation, exception.violations.single())
    }

    @Test
    fun validateAllValidatesSingleRule() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test" validateAll rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateAllWithIteratorValidatesAllRules() {
        val result = "test" validateAll rules.iterator()

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllWithIterableValidatesAllRules() {
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test" validateAll rules

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllWithNoArgumentsReturnsValid() {
        val result = "test".validateAll()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateAllWithSingleRuleValidatesRule() {
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAll(rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateAllWithVarargValidatesAllRules() {
        val result = "test".validateAll(*rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllToUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")

        val result =
            validateAllTo(destination) {
                onFailure(violation)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, destination.single())
    }

    @Test
    fun runValidatingAllToUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")

        val result =
            runValidatingAllTo(destination) {
                onFailure(violation)
                42
            }

        assertTrue(result.isFailure)
        assertEquals(violation, destination.single())
    }

    @Test
    fun validateAllToWithSingleRuleUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllTo(destination, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, destination.single())
    }

    @Test
    fun validateAllToWithIteratorUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllTo(destination, rules.iterator())

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, destination)
    }

    @Test
    fun validateAllToWithIterableUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test".validateAllTo(destination, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, destination)
    }

    @Test
    fun validateAllToWithNoRulesUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllTo(destination)

        assertIs<ValidationResult.Valid>(result)
        assertTrue(destination.isEmpty())
    }

    @Test
    fun validateAllToWithSingleRuleUsesProvidedDestinationAlternate() {
        val destination = mutableListOf<Violation>()
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllTo(destination, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, destination.single())
    }

    @Test
    fun validateAllToWithVarargUsesProvidedDestination() {
        val destination = mutableListOf<Violation>()

        val result = "test".validateAllTo(destination, *rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, destination)
    }

    @Test
    fun validateAllUsingUsesProvidedContext() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")

        val result =
            validateAllUsing(customContext) {
                onFailure(violation)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun runValidatingAllUsingUsesProvidedContext() {
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
    fun validateAllUsingWithSingleRuleUsesProvidedContext() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllUsing(customContext, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateAllUsingWithIteratorUsesProvidedContext() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllUsing(customContext, rules.iterator())

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllUsingWithIterableUsesProvidedContext() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val rules: Iterable<Rule<String>> = violations.toFailingRules()

        val result = "test".validateAllUsing(customContext, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun validateAllUsingWithNoRulesReturnsValid() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllUsing(customContext)

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun validateAllUsingWithSingleRuleUsesProvidedContextAlternate() {
        val customContext = AggregatingValidationContext(mutableListOf())
        val violation = violation("error")
        val rule = FailingRule<String>(violation)

        val result = "test".validateAllUsing(customContext, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun validateAllUsingWithVarargUsesProvidedContext() {
        val customContext = AggregatingValidationContext(mutableListOf())

        val result = "test".validateAllUsing(customContext, *rules.toTypedArray())

        assertIs<ValidationResult.Invalid>(result)
        assertContainsAllWithOrder(violations, result.violations)
    }

    @Test
    fun aggregatingValidationContextCanBeExtended() {
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
    fun validateAllWorksWithAnyTypeIncludingNull() {
        val value: Any? = null
        val violation = violation("error")
        val rule = FailingRule<Any?>(violation)

        val result =
            validateAll {
                value.applyRule(rule)
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }
}

@Suppress("UnusedPrivateClass", "Unused")
private class AggregatingValidationContextCanBeExtended(
    violationsStorage: MutableCollection<Violation>,
) : AggregatingValidationContext(violationsStorage) {
    override fun build(): ValidationResult = TODO()
}
