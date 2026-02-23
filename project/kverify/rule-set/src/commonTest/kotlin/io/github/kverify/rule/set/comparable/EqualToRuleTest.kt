package io.github.kverify.rule.set.comparable

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class EqualToRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueEqualsExpected() {
        val scope = CollectingValidationScope()
        val check = EqualToCheck(expected = 5)

        assertTrue(check.isValid(scope, 5))
    }

    @Test
    fun checkReturnsFalseWhenValueIsLessThanExpected() {
        val scope = CollectingValidationScope()
        val check = EqualToCheck(expected = 5)

        assertFalse(check.isValid(scope, 4))
    }

    @Test
    fun checkReturnsFalseWhenValueIsGreaterThanExpected() {
        val scope = CollectingValidationScope()
        val check = EqualToCheck(expected = 5)

        assertFalse(check.isValid(scope, 6))
    }

    @Test
    fun checkStoresExpected() {
        val check = EqualToCheck(expected = 42)

        assertEquals(42, check.expected)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = EqualToViolationFactory(expected = 5)

        val violation = factory.createViolation(scope, 3)

        assertIs<EqualToViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = EqualToViolationFactory(expected = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals(5, violation.expected)
        assertEquals(3, violation.actual)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = EqualToViolationFactory(expected = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals("Value must be equal to 5. Actual: 3", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = EqualToViolationFactory(expected = 5, reason = "custom reason")

        val violation = factory.createViolation(scope, 3)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = EqualToViolationFactory(expected = 5)

        val violation = factory.createViolation(scope, 3)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = EqualToViolationFactory(expected = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresExpected() {
        val factory = EqualToViolationFactory(expected = 42)

        assertEquals(42, factory.expected)
    }

    @Test
    fun factoryStoresReason() {
        val factory = EqualToViolationFactory(expected = 42, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = EqualToViolationFactory(expected = 42)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresExpected() {
        val rule = EqualToRule(expected = 10)

        assertEquals(10, rule.expected)
    }

    @Test
    fun ruleCheckIsEqualToCheck() {
        val rule = EqualToRule(expected = 10)

        assertIs<EqualToCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingExpected() {
        val rule = EqualToRule(expected = 10)
        val check = rule.validationCheck as EqualToCheck<Int>

        assertEquals(10, check.expected)
    }

    @Test
    fun ruleFactoryIsEqualToViolationFactory() {
        val rule = EqualToRule(expected = 10)

        assertIs<EqualToViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingExpected() {
        val rule = EqualToRule(expected = 10)
        val factory = rule.violationFactory as EqualToViolationFactory<Int>

        assertEquals(10, factory.expected)
    }

    // endregion
}
