package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class GreaterThanRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueIsGreaterThanMin() {
        val scope = CollectingValidationScope()
        val check = GreaterThanCheck(min = 5)

        assertTrue(check.isValid(scope, 6))
    }

    @Test
    fun checkReturnsFalseWhenValueEqualsMin() {
        val scope = CollectingValidationScope()
        val check = GreaterThanCheck(min = 5)

        assertFalse(check.isValid(scope, 5))
    }

    @Test
    fun checkReturnsFalseWhenValueIsLessThanMin() {
        val scope = CollectingValidationScope()
        val check = GreaterThanCheck(min = 5)

        assertFalse(check.isValid(scope, 4))
    }

    @Test
    fun checkStoresMin() {
        val check = GreaterThanCheck(min = 42)

        assertEquals(42, check.min)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = GreaterThanViolationFactory(min = 5)

        val violation = factory.createViolation(scope, 3)

        assertIs<GreaterThanViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = GreaterThanViolationFactory(min = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals(5, violation.minExclusive)
        assertEquals(3, violation.actual)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = GreaterThanViolationFactory(min = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals("Value must be greater than 5. Actual: 3", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = GreaterThanViolationFactory(min = 5, reason = "custom reason")

        val violation = factory.createViolation(scope, 3)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = GreaterThanViolationFactory(min = 5)

        val violation = factory.createViolation(scope, 3)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = GreaterThanViolationFactory(min = 5)

        val violation = factory.createViolation(scope, 3)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMin() {
        val factory = GreaterThanViolationFactory(min = 42)

        assertEquals(42, factory.min)
    }

    @Test
    fun factoryStoresReason() {
        val factory = GreaterThanViolationFactory(min = 42, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = GreaterThanViolationFactory(min = 42)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMin() {
        val rule = GreaterThanRule(min = 10)

        assertEquals(10, rule.min)
    }

    @Test
    fun ruleCheckIsGreaterThanCheck() {
        val rule = GreaterThanRule(min = 10)

        assertIs<GreaterThanCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingMin() {
        val rule = GreaterThanRule(min = 10)
        val check = rule.validationCheck as GreaterThanCheck<Int>

        assertEquals(10, check.min)
    }

    @Test
    fun ruleFactoryIsGreaterThanViolationFactory() {
        val rule = GreaterThanRule(min = 10)

        assertIs<GreaterThanViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingMin() {
        val rule = GreaterThanRule(min = 10)
        val factory = rule.violationFactory as GreaterThanViolationFactory<Int>

        assertEquals(10, factory.min)
    }

    // endregion
}
