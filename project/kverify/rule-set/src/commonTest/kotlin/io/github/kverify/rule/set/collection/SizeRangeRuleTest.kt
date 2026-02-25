package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class SizeRangeRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenSizeEqualsMin() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 2, max = 5)

        assertTrue(check.isValid(scope, listOf(1, 2)))
    }

    @Test
    fun checkReturnsTrueWhenSizeEqualsMax() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 2, max = 5)

        assertTrue(check.isValid(scope, listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun checkReturnsTrueWhenSizeIsWithinRange() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 2, max = 5)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsFalseWhenSizeIsBelowMin() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 2, max = 5)

        assertFalse(check.isValid(scope, listOf(1)))
    }

    @Test
    fun checkReturnsFalseWhenSizeIsAboveMax() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 2, max = 5)

        assertFalse(check.isValid(scope, listOf(1, 2, 3, 4, 5, 6)))
    }

    @Test
    fun checkReturnsTrueWhenMinEqualsMax() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 3, max = 3)

        assertTrue(check.isValid(scope, listOf(1, 2, 3)))
    }

    @Test
    fun checkReturnsFalseForEmptyCollectionWhenMinIsOne() {
        val scope = CollectingValidationScope()
        val check = SizeRangeCheck(min = 1, max = 5)

        assertFalse(check.isValid(scope, emptyList<Int>()))
    }

    @Test
    fun checkStoresMinAndMaxParameters() {
        val check = SizeRangeCheck(min = 2, max = 10)

        assertEquals(2, check.min)
        assertEquals(10, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesSizeRangeViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = SizeRangeViolationFactory(min = 2, max = 5)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<SizeRangeViolation>(violation)
        assertEquals(2, violation.minSizeAllowed)
        assertEquals(5, violation.maxSizeAllowed)
        assertEquals(1, violation.actualSize)
        assertEquals("Collection must have between 2 and 5 elements. Actual size: 1", violation.reason)
    }

    @Test
    fun factoryCreatesSizeRangeViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = SizeRangeViolationFactory(min = 2, max = 5, reason = "Out of range")

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<SizeRangeViolation>(violation)
        assertEquals("Out of range", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = SizeRangeViolationFactory(min = 2, max = 5)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<SizeRangeViolation>(violation)
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
        val factory = SizeRangeViolationFactory(min = 2, max = 5)

        val violation = factory.createViolation(scope, listOf(1))

        assertIs<SizeRangeViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMinAndMaxParameters() {
        val rule = SizeRangeRule<List<Int>>(min = 2, max = 10)

        assertEquals(2, rule.min)
        assertEquals(10, rule.max)
    }

    @Test
    fun ruleHasSizeRangeCheckWithMatchingParameters() {
        val rule = SizeRangeRule<List<Int>>(min = 2, max = 10)

        val check = rule.validationCheck
        assertIs<SizeRangeCheck>(check)
        assertEquals(2, check.min)
        assertEquals(10, check.max)
    }

    @Test
    fun ruleHasSizeRangeViolationFactoryByDefault() {
        val rule = SizeRangeRule<List<Int>>(min = 2, max = 10)

        val factory = rule.violationFactory
        assertIs<SizeRangeViolationFactory>(factory)
        assertEquals(2, factory.min)
        assertEquals(10, factory.max)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = SizeRangeViolationFactory(min = 2, max = 10, reason = "custom")
        val rule = SizeRangeRule<List<Int>>(min = 2, max = 10, violationFactory = factory)

        assertIs<SizeRangeViolationFactory>(rule.violationFactory)
    }

    // endregion
}
