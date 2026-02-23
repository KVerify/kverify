package io.github.kverify.rule.set.comparable

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class BetweenRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueEqualsMin() {
        val scope = CollectingValidationScope()
        val check = BetweenCheck(min = 1, max = 10)

        assertTrue(check.isValid(scope, 1))
    }

    @Test
    fun checkReturnsTrueWhenValueEqualsMax() {
        val scope = CollectingValidationScope()
        val check = BetweenCheck(min = 1, max = 10)

        assertTrue(check.isValid(scope, 10))
    }

    @Test
    fun checkReturnsTrueWhenValueIsBetweenMinAndMax() {
        val scope = CollectingValidationScope()
        val check = BetweenCheck(min = 1, max = 10)

        assertTrue(check.isValid(scope, 5))
    }

    @Test
    fun checkReturnsFalseWhenValueIsBelowMin() {
        val scope = CollectingValidationScope()
        val check = BetweenCheck(min = 1, max = 10)

        assertFalse(check.isValid(scope, 0))
    }

    @Test
    fun checkReturnsFalseWhenValueIsAboveMax() {
        val scope = CollectingValidationScope()
        val check = BetweenCheck(min = 1, max = 10)

        assertFalse(check.isValid(scope, 11))
    }

    @Test
    fun checkStoresMin() {
        val check = BetweenCheck(min = 3, max = 7)

        assertEquals(3, check.min)
    }

    @Test
    fun checkStoresMax() {
        val check = BetweenCheck(min = 3, max = 7)

        assertEquals(7, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = BetweenViolationFactory(min = 1, max = 10)

        val violation = factory.createViolation(scope, 15)

        assertIs<BetweenViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = BetweenViolationFactory(min = 1, max = 10)

        val violation = factory.createViolation(scope, 15)

        assertEquals(1, violation.min)
        assertEquals(10, violation.max)
        assertEquals(15, violation.actual)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = BetweenViolationFactory(min = 1, max = 10)

        val violation = factory.createViolation(scope, 15)

        assertEquals("Value must be between 1 and 10. Actual: 15", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = BetweenViolationFactory(min = 1, max = 10, reason = "custom reason")

        val violation = factory.createViolation(scope, 15)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = BetweenViolationFactory(min = 1, max = 10)

        val violation = factory.createViolation(scope, 15)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = BetweenViolationFactory(min = 1, max = 10)

        val violation = factory.createViolation(scope, 15)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMin() {
        val factory = BetweenViolationFactory(min = 3, max = 7)

        assertEquals(3, factory.min)
    }

    @Test
    fun factoryStoresMax() {
        val factory = BetweenViolationFactory(min = 3, max = 7)

        assertEquals(7, factory.max)
    }

    @Test
    fun factoryStoresReason() {
        val factory = BetweenViolationFactory(min = 3, max = 7, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = BetweenViolationFactory(min = 3, max = 7)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMin() {
        val rule = BetweenRule(min = 1, max = 10)

        assertEquals(1, rule.min)
    }

    @Test
    fun ruleStoresMax() {
        val rule = BetweenRule(min = 1, max = 10)

        assertEquals(10, rule.max)
    }

    @Test
    fun ruleCheckIsBetweenCheck() {
        val rule = BetweenRule(min = 1, max = 10)

        assertIs<BetweenCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingMin() {
        val rule = BetweenRule(min = 1, max = 10)
        val check = rule.validationCheck as BetweenCheck<Int>

        assertEquals(1, check.min)
    }

    @Test
    fun ruleCheckHasMatchingMax() {
        val rule = BetweenRule(min = 1, max = 10)
        val check = rule.validationCheck as BetweenCheck<Int>

        assertEquals(10, check.max)
    }

    @Test
    fun ruleFactoryIsBetweenViolationFactory() {
        val rule = BetweenRule(min = 1, max = 10)

        assertIs<BetweenViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingMin() {
        val rule = BetweenRule(min = 1, max = 10)
        val factory = rule.violationFactory as BetweenViolationFactory<Int>

        assertEquals(1, factory.min)
    }

    @Test
    fun ruleFactoryHasMatchingMax() {
        val rule = BetweenRule(min = 1, max = 10)
        val factory = rule.violationFactory as BetweenViolationFactory<Int>

        assertEquals(10, factory.max)
    }

    // endregion
}
