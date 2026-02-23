package io.github.kverify.rule.set.collection

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MaxSizeRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenSizeEqualsMax() {
        val scope = CollectingValidationScope()
        val check = MaxSizeCheck(max = 3)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsTrueWhenSizeBelowMax() {
        val scope = CollectingValidationScope()
        val check = MaxSizeCheck(max = 5)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsFalseWhenSizeExceedsMax() {
        val scope = CollectingValidationScope()
        val check = MaxSizeCheck(max = 2)

        assertFalse(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsTrueForEmptyCollectionWhenMaxIsZero() {
        val scope = CollectingValidationScope()
        val check = MaxSizeCheck(max = 0)

        assertTrue(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkReturnsFalseForNonEmptyCollectionWhenMaxIsZero() {
        val scope = CollectingValidationScope()
        val check = MaxSizeCheck(max = 0)

        assertFalse(check.isValid(scope, listOf(1)))
    }

    @Test
    fun checkStoresMaxParameter() {
        val check = MaxSizeCheck(max = 10)

        assertEquals(10, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesMaxSizeViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = MaxSizeViolationFactory(max = 2)

        val violation = factory.createViolation(scope, listOf(1, 2, 3))

        assertIs<MaxSizeViolation>(violation)
        assertEquals(2, violation.maxSizeAllowed)
        assertEquals(3, violation.actualSize)
        assertEquals("Collection must have at most 2 elements. Actual size: 3", violation.reason)
    }

    @Test
    fun factoryCreatesMaxSizeViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = MaxSizeViolationFactory(max = 2, reason = "Too many items")

        val violation = factory.createViolation(scope, listOf(1, 2, 3))

        assertIs<MaxSizeViolation>(violation)
        assertEquals("Too many items", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = MaxSizeViolationFactory(max = 2)

        val violation = factory.createViolation(scope, listOf(1, 2, 3))

        assertIs<MaxSizeViolation>(violation)
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
        val factory = MaxSizeViolationFactory(max = 2)

        val violation = factory.createViolation(scope, listOf(1, 2, 3))

        assertIs<MaxSizeViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMaxParameter() {
        val rule = MaxSizeRule<List<Int>>(max = 10)

        assertEquals(10, rule.max)
    }

    @Test
    fun ruleHasMaxSizeCheckWithMatchingMax() {
        val rule = MaxSizeRule<List<Int>>(max = 10)

        val check = rule.validationCheck
        assertIs<MaxSizeCheck>(check)
        assertEquals(10, check.max)
    }

    @Test
    fun ruleHasMaxSizeViolationFactoryByDefault() {
        val rule = MaxSizeRule<List<Int>>(max = 10)

        val factory = rule.violationFactory
        assertIs<MaxSizeViolationFactory>(factory)
        assertEquals(10, factory.max)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = MaxSizeViolationFactory(max = 10, reason = "custom")
        val rule = MaxSizeRule<List<Int>>(max = 10, violationFactory = factory)

        assertIs<MaxSizeViolationFactory>(rule.violationFactory)
    }

    // endregion
}
