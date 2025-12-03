package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.util.FailingRule
import io.github.kverify.core.util.PassingRule
import io.github.kverify.core.util.violations
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.violation
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class CollectingValidationContextTest {
    private lateinit var storage: MutableList<Violation>
    private lateinit var context: CollectingValidationContext

    private val violationList = violations("error1", "error2")
    private val rules = violationList.map { FailingRule<String>(it) }

    private val violation = violation("error")
    private val rule = FailingRule<String>(violation)

    @BeforeTest
    fun setUp() {
        storage = mutableListOf()
        context = CollectingValidationContext(storage)
    }

    @Test
    fun collectingValidationContextCollectsViolations() {
        violationList.forEach { context.onFailure(it) }

        assertEquals(violationList, storage)
    }

    @Test
    fun buildReturnsValidWhenNoViolations() {
        val result = context.build()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun buildReturnsInvalidWithViolations() {
        violationList.forEach { context.onFailure(it) }
        val result = context.build()

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun verifyCollectingWithNoViolations() {
        val result =
            verifyCollecting {
                "test" verifyWith PassingRule
            }

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun verifyCollectingWithViolations() {
        val result =
            verifyCollecting {
                "test" verifyWith rules[0]
                "test" verifyWith rules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun valueVerifyWithCollectingNoRules() {
        val result = "test".verifyWithCollecting()

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun valueVerifyWithCollectingSingleRule() {
        val result = "test" verifyWithCollecting rule

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
    }

    @Test
    fun valueVerifyWithCollectingVarargRules() {
        val result = "test".verifyWithCollecting(rules[0], rules[1])

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun valueVerifyWithCollectingIterableRules() {
        val result = "test" verifyWithCollecting rules

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }

    @Test
    fun verifyCollectingToWithProvidedDestination() {
        val result =
            verifyCollectingTo(storage) {
                "test" verifyWith rules[0]
                "test" verifyWith rules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithCollectingToNoRules() {
        val result = "test".verifyWithCollectingTo(storage)

        assertIs<ValidationResult.Valid>(result)
        assertTrue(storage.isEmpty())
    }

    @Test
    fun valueVerifyWithCollectingToSingleRule() {
        val result = "test".verifyWithCollectingTo(storage, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
        assertEquals(violation, storage.single())
    }

    @Test
    fun valueVerifyWithCollectingToVarargRules() {
        val result = "test".verifyWithCollectingTo(storage, rules[0], rules[1])

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithCollectingToIterableRules() {
        val result = "test".verifyWithCollectingTo(storage, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun verifyCollectingUsingCustomContext() {
        val result =
            verifyCollectingUsing(context) {
                "test" verifyWith rules[0]
                "test" verifyWith rules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithCollectingUsingNoRules() {
        val result = "test".verifyWithCollectingUsing(context)

        assertIs<ValidationResult.Valid>(result)
    }

    @Test
    fun valueVerifyWithCollectingUsingSingleRule() {
        val result = "test".verifyWithCollectingUsing(context, rule)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violation, result.violations.single())
        assertEquals(violation, storage.single())
    }

    @Test
    fun valueVerifyWithCollectingUsingVarargRules() {
        val result = "test".verifyWithCollectingUsing(context, rules[0], rules[1])

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun valueVerifyWithCollectingUsingIterableRules() {
        val result = "test".verifyWithCollectingUsing(context, rules)

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
        assertEquals(violationList, storage)
    }

    @Test
    fun verifyCollectingWithMixedPassingAndFailingRules() {
        val result =
            verifyCollecting {
                "test" verifyWith PassingRule
                "test" verifyWith rules[0]
                "test" verifyWith PassingRule
                "test" verifyWith rules[1]
            }

        assertIs<ValidationResult.Invalid>(result)
        assertEquals(violationList, result.violations)
    }
}
