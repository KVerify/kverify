package io.github.kverify.rule.set.collection

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MinSizeRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenSizeEqualsMin() {
        val scope = CollectingValidationScope()
        val check = MinSizeCheck(min = 3)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsTrueWhenSizeExceedsMin() {
        val scope = CollectingValidationScope()
        val check = MinSizeCheck(min = 2)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsFalseWhenSizeBelowMin() {
        val scope = CollectingValidationScope()
        val check = MinSizeCheck(min = 3)

        assertFalse(check.isValid(scope, listOf(1, 2)))
    }

    @Test
    fun checkReturnsTrueForEmptyCollectionWhenMinIsZero() {
        val scope = CollectingValidationScope()
        val check = MinSizeCheck(min = 0)

        assertTrue(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkReturnsFalseForEmptyCollectionWhenMinIsOne() {
        val scope = CollectingValidationScope()
        val check = MinSizeCheck(min = 1)

        assertFalse(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkStoresMinParameter() {
        val check = MinSizeCheck(min = 5)

        assertEquals(5, check.min)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesMinSizeViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = MinSizeViolationFactory(min = 3)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<MinSizeViolation>(violation)
        assertEquals(3, violation.minSizeAllowed)
        assertEquals(1, violation.actualSize)
        assertEquals("Collection must have at least 3 elements. Actual size: 1", violation.reason)
    }

    @Test
    fun factoryCreatesMinSizeViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = MinSizeViolationFactory(min = 3, reason = "Too few items")

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<MinSizeViolation>(violation)
        assertEquals("Too few items", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = MinSizeViolationFactory(min = 3)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<MinSizeViolation>(violation)
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
        val factory = MinSizeViolationFactory(min = 3)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<MinSizeViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMinParameter() {
        val rule = MinSizeRule<List<Int>>(min = 5)

        assertEquals(5, rule.min)
    }

    @Test
    fun ruleHasMinSizeCheckWithMatchingMin() {
        val rule = MinSizeRule<List<Int>>(min = 5)

        val check = rule.validationCheck
        assertIs<MinSizeCheck>(check)
        assertEquals(5, check.min)
    }

    @Test
    fun ruleHasMinSizeViolationFactoryByDefault() {
        val rule = MinSizeRule<List<Int>>(min = 5)

        val factory = rule.violationFactory
        assertIs<MinSizeViolationFactory>(factory)
        assertEquals(5, factory.min)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = MinSizeViolationFactory(min = 5, reason = "custom")
        val rule = MinSizeRule<List<Int>>(min = 5, violationFactory = factory)

        assertIs<MinSizeViolationFactory>(rule.violationFactory)
    }

    // endregion
}
