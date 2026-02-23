package io.github.kverify.rule.set.string

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class MinLengthRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenLengthExceedsMin() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 3)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueWhenLengthEqualsMin() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 5)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsFalseWhenLengthBelowMin() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 5)

        assertFalse(check.isValid(scope, "hi"))
    }

    @Test
    fun checkReturnsTrueForEmptyStringWhenMinIsZero() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 0)

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForEmptyStringWhenMinIsOne() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 1)

        assertFalse(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsTrueAtBoundary() {
        val scope = CollectingValidationScope()
        val check = MinLengthCheck(min = 3)

        assertTrue(check.isValid(scope, "abc"))
        assertFalse(check.isValid(scope, "ab"))
    }

    @Test
    fun checkStoresMinParameter() {
        val check = MinLengthCheck(min = 7)

        assertEquals(7, check.min)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesMinLengthViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = MinLengthViolationFactory(min = 5)

        val violation = factory.createViolation(scope, "ab")

        assertIs<MinLengthViolation>(violation)
        assertEquals(5, violation.minLengthAllowed)
        assertEquals(2, violation.actualLength)
        assertEquals(
            "Value must be at least 5 characters long. Actual length: 2",
            violation.reason,
        )
    }

    @Test
    fun factoryCreatesMinLengthViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = MinLengthViolationFactory(min = 3, reason = "Too short")

        val violation = factory.createViolation(scope, "x")

        assertIs<MinLengthViolation>(violation)
        assertEquals("Too short", violation.reason)
        assertEquals(3, violation.minLengthAllowed)
        assertEquals(1, violation.actualLength)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = MinLengthViolationFactory(min = 5)

        val violation = factory.createViolation(scope, "ab")

        assertIs<MinLengthViolation>(violation)
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
        val factory = MinLengthViolationFactory(min = 5)

        val violation = factory.createViolation(scope, "ab")

        assertIs<MinLengthViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMinParameter() {
        val factory = MinLengthViolationFactory(min = 10)

        assertEquals(10, factory.min)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMinParameter() {
        val rule = MinLengthRule(min = 5)

        assertEquals(5, rule.min)
    }

    @Test
    fun ruleHasMinLengthCheckWithMatchingMin() {
        val rule = MinLengthRule(min = 5)

        assertIs<MinLengthCheck>(rule.validationCheck)
        assertEquals(5, (rule.validationCheck as MinLengthCheck).min)
    }

    @Test
    fun ruleHasMinLengthViolationFactoryWithMatchingMin() {
        val rule = MinLengthRule(min = 5)

        assertIs<MinLengthViolationFactory>(rule.violationFactory)
        assertEquals(5, (rule.violationFactory as MinLengthViolationFactory).min)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = MinLengthViolationFactory(min = 5, reason = "custom")
        val rule = MinLengthRule(min = 5, violationFactory = factory)

        assertEquals(factory, rule.violationFactory)
    }

    // endregion
}
