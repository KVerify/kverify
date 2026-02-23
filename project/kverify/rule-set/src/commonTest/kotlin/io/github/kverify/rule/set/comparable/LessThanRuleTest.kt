package io.github.kverify.rule.set.comparable

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LessThanRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueIsLessThanMax() {
        val scope = CollectingValidationScope()
        val check = LessThanCheck(max = 5)

        assertTrue(check.isValid(scope, 4))
    }

    @Test
    fun checkReturnsFalseWhenValueEqualsMax() {
        val scope = CollectingValidationScope()
        val check = LessThanCheck(max = 5)

        assertFalse(check.isValid(scope, 5))
    }

    @Test
    fun checkReturnsFalseWhenValueIsGreaterThanMax() {
        val scope = CollectingValidationScope()
        val check = LessThanCheck(max = 5)

        assertFalse(check.isValid(scope, 6))
    }

    @Test
    fun checkStoresMax() {
        val check = LessThanCheck(max = 42)

        assertEquals(42, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = LessThanViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertIs<LessThanViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = LessThanViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals(5, violation.maxExclusive)
        assertEquals(7, violation.actual)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = LessThanViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals("Value must be less than 5. Actual: 7", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = LessThanViolationFactory(max = 5, reason = "custom reason")

        val violation = factory.createViolation(scope, 7)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = LessThanViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = LessThanViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMax() {
        val factory = LessThanViolationFactory(max = 42)

        assertEquals(42, factory.max)
    }

    @Test
    fun factoryStoresReason() {
        val factory = LessThanViolationFactory(max = 42, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = LessThanViolationFactory(max = 42)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMax() {
        val rule = LessThanRule(max = 10)

        assertEquals(10, rule.max)
    }

    @Test
    fun ruleCheckIsLessThanCheck() {
        val rule = LessThanRule(max = 10)

        assertIs<LessThanCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingMax() {
        val rule = LessThanRule(max = 10)
        val check = rule.validationCheck as LessThanCheck<Int>

        assertEquals(10, check.max)
    }

    @Test
    fun ruleFactoryIsLessThanViolationFactory() {
        val rule = LessThanRule(max = 10)

        assertIs<LessThanViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingMax() {
        val rule = LessThanRule(max = 10)
        val factory = rule.violationFactory as LessThanViolationFactory<Int>

        assertEquals(10, factory.max)
    }

    // endregion
}
