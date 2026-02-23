package io.github.kverify.rule.set.string

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LengthRangeRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenLengthWithinRange() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 2, max = 10)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueWhenLengthEqualsMin() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 5, max = 10)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueWhenLengthEqualsMax() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 2, max = 5)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsFalseWhenLengthBelowMin() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 5, max = 10)

        assertFalse(check.isValid(scope, "hi"))
    }

    @Test
    fun checkReturnsFalseWhenLengthExceedsMax() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 1, max = 3)

        assertFalse(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueWhenMinEqualsMax() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 3, max = 3)

        assertTrue(check.isValid(scope, "abc"))
    }

    @Test
    fun checkReturnsFalseAtBoundaryBelowMin() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 3, max = 6)

        assertFalse(check.isValid(scope, "ab"))
        assertTrue(check.isValid(scope, "abc"))
    }

    @Test
    fun checkReturnsFalseAtBoundaryAboveMax() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 1, max = 3)

        assertTrue(check.isValid(scope, "abc"))
        assertFalse(check.isValid(scope, "abcd"))
    }

    @Test
    fun checkReturnsTrueForEmptyStringWhenMinIsZero() {
        val scope = CollectingValidationScope()
        val check = LengthRangeCheck(min = 0, max = 5)

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkStoresMinAndMaxParameters() {
        val check = LengthRangeCheck(min = 2, max = 10)

        assertEquals(2, check.min)
        assertEquals(10, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesLengthRangeViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = LengthRangeViolationFactory(min = 3, max = 10)

        val violation = factory.createViolation(scope, "ab")

        assertIs<LengthRangeViolation>(violation)
        assertEquals(3, violation.minLengthAllowed)
        assertEquals(10, violation.maxLengthAllowed)
        assertEquals(2, violation.actualLength)
        assertEquals(
            "Value must be between 3 and 10 characters long. Actual length: 2",
            violation.reason,
        )
    }

    @Test
    fun factoryCreatesLengthRangeViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = LengthRangeViolationFactory(min = 1, max = 5, reason = "Invalid length")

        val violation = factory.createViolation(scope, "too long string")

        assertIs<LengthRangeViolation>(violation)
        assertEquals("Invalid length", violation.reason)
        assertEquals(1, violation.minLengthAllowed)
        assertEquals(5, violation.maxLengthAllowed)
        assertEquals(15, violation.actualLength)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = LengthRangeViolationFactory(min = 3, max = 10)

        val violation = factory.createViolation(scope, "ab")

        assertIs<LengthRangeViolation>(violation)
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
        val factory = LengthRangeViolationFactory(min = 3, max = 10)

        val violation = factory.createViolation(scope, "ab")

        assertIs<LengthRangeViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMinAndMaxParameters() {
        val factory = LengthRangeViolationFactory(min = 2, max = 20)

        assertEquals(2, factory.min)
        assertEquals(20, factory.max)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMinAndMaxParameters() {
        val rule = LengthRangeRule(min = 3, max = 10)

        assertEquals(3, rule.min)
        assertEquals(10, rule.max)
    }

    @Test
    fun ruleHasLengthRangeCheckWithMatchingMinAndMax() {
        val rule = LengthRangeRule(min = 3, max = 10)

        assertIs<LengthRangeCheck>(rule.validationCheck)
        val check = rule.validationCheck as LengthRangeCheck
        assertEquals(3, check.min)
        assertEquals(10, check.max)
    }

    @Test
    fun ruleHasLengthRangeViolationFactoryWithMatchingMinAndMax() {
        val rule = LengthRangeRule(min = 3, max = 10)

        assertIs<LengthRangeViolationFactory>(rule.violationFactory)
        val factory = rule.violationFactory as LengthRangeViolationFactory
        assertEquals(3, factory.min)
        assertEquals(10, factory.max)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = LengthRangeViolationFactory(min = 3, max = 10, reason = "custom")
        val rule = LengthRangeRule(min = 3, max = 10, violationFactory = factory)

        assertEquals(factory, rule.violationFactory)
    }

    // endregion
}
