package io.github.kverify.rule.set.comparable

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class NotEqualToRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueDiffersFromForbidden() {
        val scope = CollectingValidationScope()
        val check = NotEqualToCheck(forbidden = 5)

        assertTrue(check.isValid(scope, 4))
    }

    @Test
    fun checkReturnsTrueWhenValueIsGreaterThanForbidden() {
        val scope = CollectingValidationScope()
        val check = NotEqualToCheck(forbidden = 5)

        assertTrue(check.isValid(scope, 6))
    }

    @Test
    fun checkReturnsFalseWhenValueEqualsForbidden() {
        val scope = CollectingValidationScope()
        val check = NotEqualToCheck(forbidden = 5)

        assertFalse(check.isValid(scope, 5))
    }

    @Test
    fun checkStoresForbidden() {
        val check = NotEqualToCheck(forbidden = 42)

        assertEquals(42, check.forbidden)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = NotEqualToViolationFactory(forbidden = 5)

        val violation = factory.createViolation(scope, 5)

        assertIs<NotEqualToViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = NotEqualToViolationFactory(forbidden = 5)

        val violation = factory.createViolation(scope, 5)

        assertEquals(5, violation.forbidden)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = NotEqualToViolationFactory(forbidden = 5)

        val violation = factory.createViolation(scope, 5)

        assertEquals("Value must not be equal to 5", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = NotEqualToViolationFactory(forbidden = 5, reason = "custom reason")

        val violation = factory.createViolation(scope, 5)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = NotEqualToViolationFactory(forbidden = 5)

        val violation = factory.createViolation(scope, 5)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = NotEqualToViolationFactory(forbidden = 5)

        val violation = factory.createViolation(scope, 5)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresForbidden() {
        val factory = NotEqualToViolationFactory(forbidden = 42)

        assertEquals(42, factory.forbidden)
    }

    @Test
    fun factoryStoresReason() {
        val factory = NotEqualToViolationFactory(forbidden = 42, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = NotEqualToViolationFactory(forbidden = 42)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresForbidden() {
        val rule = NotEqualToRule(forbidden = 10)

        assertEquals(10, rule.forbidden)
    }

    @Test
    fun ruleCheckIsNotEqualToCheck() {
        val rule = NotEqualToRule(forbidden = 10)

        assertIs<NotEqualToCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingForbidden() {
        val rule = NotEqualToRule(forbidden = 10)
        val check = rule.validationCheck as NotEqualToCheck<Int>

        assertEquals(10, check.forbidden)
    }

    @Test
    fun ruleFactoryIsNotEqualToViolationFactory() {
        val rule = NotEqualToRule(forbidden = 10)

        assertIs<NotEqualToViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingForbidden() {
        val rule = NotEqualToRule(forbidden = 10)
        val factory = rule.violationFactory as NotEqualToViolationFactory<Int>

        assertEquals(10, factory.forbidden)
    }

    // endregion
}
