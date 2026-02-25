package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AtMostRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueEqualsMax() {
        val scope = CollectingValidationScope()
        val check = AtMostCheck(max = 5)

        assertTrue(check.isValid(scope, 5))
    }

    @Test
    fun checkReturnsTrueWhenValueIsLessThanMax() {
        val scope = CollectingValidationScope()
        val check = AtMostCheck(max = 5)

        assertTrue(check.isValid(scope, 4))
    }

    @Test
    fun checkReturnsFalseWhenValueIsGreaterThanMax() {
        val scope = CollectingValidationScope()
        val check = AtMostCheck(max = 5)

        assertFalse(check.isValid(scope, 6))
    }

    @Test
    fun checkStoresMax() {
        val check = AtMostCheck(max = 42)

        assertEquals(42, check.max)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesViolationWithCorrectType() {
        val scope = CollectingValidationScope()
        val factory = AtMostViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertIs<AtMostViolation<Int>>(violation)
    }

    @Test
    fun factoryCreatesViolationWithCorrectFields() {
        val scope = CollectingValidationScope()
        val factory = AtMostViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals(5, violation.maxAllowed)
        assertEquals(7, violation.actual)
    }

    @Test
    fun factoryCreatesViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = AtMostViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals("Value must be at most 5. Actual: 7", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = AtMostViolationFactory(max = 5, reason = "custom reason")

        val violation = factory.createViolation(scope, 7)

        assertEquals("custom reason", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPath() {
        val scope = CollectingValidationScope()
        val factory = AtMostViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertTrue(violation.validationPath.isEmpty())
    }

    @Test
    fun factoryCreatesViolationWithPath() {
        val scope =
            CollectingValidationScope(
                validationContext = ListValidationContext(ValidationPathElement.Property("field")),
            )
        val factory = AtMostViolationFactory(max = 5)

        val violation = factory.createViolation(scope, 7)

        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresMax() {
        val factory = AtMostViolationFactory(max = 42)

        assertEquals(42, factory.max)
    }

    @Test
    fun factoryStoresReason() {
        val factory = AtMostViolationFactory(max = 42, reason = "my reason")

        assertEquals("my reason", factory.reason)
    }

    @Test
    fun factoryStoresNullReasonByDefault() {
        val factory = AtMostViolationFactory(max = 42)

        assertEquals(null, factory.reason)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresMax() {
        val rule = AtMostRule(max = 10)

        assertEquals(10, rule.max)
    }

    @Test
    fun ruleCheckIsAtMostCheck() {
        val rule = AtMostRule(max = 10)

        assertIs<AtMostCheck<Int>>(rule.validationCheck)
    }

    @Test
    fun ruleCheckHasMatchingMax() {
        val rule = AtMostRule(max = 10)
        val check = rule.validationCheck as AtMostCheck<Int>

        assertEquals(10, check.max)
    }

    @Test
    fun ruleFactoryIsAtMostViolationFactory() {
        val rule = AtMostRule(max = 10)

        assertIs<AtMostViolationFactory<Int>>(rule.violationFactory)
    }

    @Test
    fun ruleFactoryHasMatchingMax() {
        val rule = AtMostRule(max = 10)
        val factory = rule.violationFactory as AtMostViolationFactory<Int>

        assertEquals(10, factory.max)
    }

    // endregion
}
