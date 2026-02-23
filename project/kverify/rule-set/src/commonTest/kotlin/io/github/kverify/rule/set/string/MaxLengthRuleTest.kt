package io.github.kverify.rule.set.string

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MaxLengthRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenLengthBelowMax() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 10)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueWhenLengthEqualsMax() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 5)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsFalseWhenLengthExceedsMax() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 3)

        assertFalse(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueForEmptyStringWhenMaxIsZero() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 0)

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForSingleCharWhenMaxIsZero() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 0)

        assertFalse(check.isValid(scope, "a"))
    }

    @Test
    fun checkReturnsTrueAtBoundary() {
        val scope = CollectingValidationScope()
        val check = MaxLengthCheck(max = 3)

        assertTrue(check.isValid(scope, "abc"))
        assertFalse(check.isValid(scope, "abcd"))
    }

    @Test
    fun checkStoresMaxParameter() {
        val check = MaxLengthCheck(max = 7)

        assertEquals(7, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesMaxLengthViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = MaxLengthViolationFactory(max = 3)

        val violation = factory.createViolation(scope, "hello")

        assertIs<MaxLengthViolation>(violation)
        assertEquals(3, violation.maxLengthAllowed)
        assertEquals(5, violation.actualLength)
        assertEquals(
            "Value must be at most 3 characters long. Actual length: 5",
            violation.reason,
        )
    }

    @Test
    fun factoryCreatesMaxLengthViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = MaxLengthViolationFactory(max = 10, reason = "Too long")

        val violation = factory.createViolation(scope, "this is a very long string")

        assertIs<MaxLengthViolation>(violation)
        assertEquals("Too long", violation.reason)
        assertEquals(10, violation.maxLengthAllowed)
        assertEquals(26, violation.actualLength)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = MaxLengthViolationFactory(max = 3)

        val violation = factory.createViolation(scope, "hello")

        assertIs<MaxLengthViolation>(violation)
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
        val factory = MaxLengthViolationFactory(max = 3)

        val violation = factory.createViolation(scope, "hello")

        assertIs<MaxLengthViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMaxParameter() {
        val factory = MaxLengthViolationFactory(max = 15)

        assertEquals(15, factory.max)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMaxParameter() {
        val rule = MaxLengthRule(max = 10)

        assertEquals(10, rule.max)
    }

    @Test
    fun ruleHasMaxLengthCheckWithMatchingMax() {
        val rule = MaxLengthRule(max = 10)

        assertIs<MaxLengthCheck>(rule.validationCheck)
        assertEquals(10, (rule.validationCheck as MaxLengthCheck).max)
    }

    @Test
    fun ruleHasMaxLengthViolationFactoryWithMatchingMax() {
        val rule = MaxLengthRule(max = 10)

        assertIs<MaxLengthViolationFactory>(rule.violationFactory)
        assertEquals(10, (rule.violationFactory as MaxLengthViolationFactory).max)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = MaxLengthViolationFactory(max = 10, reason = "custom")
        val rule = MaxLengthRule(max = 10, violationFactory = factory)

        assertEquals(factory, rule.violationFactory)
    }

    // endregion
}
