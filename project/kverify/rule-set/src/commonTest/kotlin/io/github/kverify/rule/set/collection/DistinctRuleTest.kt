package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class DistinctRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueForDistinctElements() {
        val scope = CollectingValidationScope()

        assertTrue(DistinctCheck.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsTrueForEmptyCollection() {
        val scope = CollectingValidationScope()

        assertTrue(DistinctCheck.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkReturnsTrueForSingleElement() {
        val scope = CollectingValidationScope()

        assertTrue(DistinctCheck.isValid(scope, listOf(1)))
    }

    @Test
    fun checkReturnsFalseForDuplicateElements() {
        val scope = CollectingValidationScope()

        assertFalse(DistinctCheck.isValid(scope, listOf(1, 2, 2, 3)))
    }

    @Test
    fun checkReturnsFalseForAllDuplicates() {
        val scope = CollectingValidationScope()

        assertFalse(DistinctCheck.isValid(scope, listOf(1, 1, 1)))
    }

    @Test
    fun checkReturnsFalseForTwoDuplicatePairs() {
        val scope = CollectingValidationScope()

        assertFalse(DistinctCheck.isValid(scope, listOf(1, 2, 1, 2)))
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesDistinctViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = DistinctViolationFactory()

        val violation = factory.createViolation(scope, listOf(1, 2, 2, 3))

        assertIs<DistinctViolation>(violation)
        assertEquals(4, violation.actualSize)
        assertEquals(3, violation.distinctSize)
        assertEquals("Collection must contain distinct elements. Found 1 duplicates", violation.reason)
    }

    @Test
    fun factoryCreatesDistinctViolationWithMultipleDuplicates() {
        val scope = CollectingValidationScope()
        val factory = DistinctViolationFactory()

        val violation = factory.createViolation(scope, listOf(1, 1, 2, 2, 3))

        assertIs<DistinctViolation>(violation)
        assertEquals(5, violation.actualSize)
        assertEquals(3, violation.distinctSize)
        assertEquals("Collection must contain distinct elements. Found 2 duplicates", violation.reason)
    }

    @Test
    fun factoryCreatesDistinctViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = DistinctViolationFactory(reason = "No duplicates allowed")

        val violation = factory.createViolation(scope, listOf(1, 1))

        assertIs<DistinctViolation>(violation)
        assertEquals("No duplicates allowed", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = DistinctViolationFactory()

        val violation = factory.createViolation(scope, listOf(1, 1))

        assertIs<DistinctViolation>(violation)
        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPathFromContext() {
        val scope =
            CollectingValidationScope(
                validationContext =
                    ListValidationContext(
                        ValidationPathElement.Property("field"),
                    ),
            )
        val factory = DistinctViolationFactory()

        val violation = factory.createViolation(scope, listOf(1, 1))

        assertIs<DistinctViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleHasDistinctCheckAsValidationCheck() {
        val rule = DistinctRule<List<Int>>()

        assertSame(DistinctCheck, rule.validationCheck)
    }

    @Test
    fun ruleHasDistinctViolationFactoryByDefault() {
        val rule = DistinctRule<List<Int>>()

        assertIs<DistinctViolationFactory>(rule.violationFactory)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = DistinctViolationFactory(reason = "custom")
        val rule = DistinctRule<List<Int>>(violationFactory = factory)

        assertIs<DistinctViolationFactory>(rule.violationFactory)
    }

    @Test
    fun companionImplementsRule() {
        assertIs<Rule<Collection<*>>>(DistinctRule.Companion)
    }

    @Test
    fun companionDelegatesCorrectly() {
        val scope = CollectingValidationScope()

        DistinctRule.execute(scope, listOf(1, 2, 3))

        assertTrue(scope.build().isValid)
    }

    @Test
    fun companionDelegatesFailureCorrectly() {
        val scope = CollectingValidationScope()

        DistinctRule.execute(scope, listOf(1, 1, 2))

        assertFalse(scope.build().isValid)
    }

    // endregion
}
