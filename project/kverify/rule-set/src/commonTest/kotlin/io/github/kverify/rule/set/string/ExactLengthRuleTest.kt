package io.github.kverify.rule.set.string

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ExactLengthRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueWhenLengthMatchesExactly() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 5)

        assertTrue(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsFalseWhenLengthIsShorter() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 5)

        assertFalse(check.isValid(scope, "hi"))
    }

    @Test
    fun checkReturnsFalseWhenLengthIsLonger() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 3)

        assertFalse(check.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueForEmptyStringWhenLengthIsZero() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 0)

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForEmptyStringWhenLengthIsOne() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 1)

        assertFalse(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForOneCharOffBoundary() {
        val scope = CollectingValidationScope()
        val check = ExactLengthCheck(length = 3)

        assertFalse(check.isValid(scope, "ab"))
        assertTrue(check.isValid(scope, "abc"))
        assertFalse(check.isValid(scope, "abcd"))
    }

    @Test
    fun checkStoresLengthParameter() {
        val check = ExactLengthCheck(length = 12)

        assertEquals(12, check.length)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesExactLengthViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = ExactLengthViolationFactory(length = 5)

        val violation = factory.createViolation(scope, "hi")

        assertIs<ExactLengthViolation>(violation)
        assertEquals(5, violation.expectedLength)
        assertEquals(2, violation.actualLength)
        assertEquals(
            "Value must be exactly 5 characters long. Actual length: 2",
            violation.reason,
        )
    }

    @Test
    fun factoryCreatesExactLengthViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = ExactLengthViolationFactory(length = 10, reason = "Wrong length")

        val violation = factory.createViolation(scope, "abc")

        assertIs<ExactLengthViolation>(violation)
        assertEquals("Wrong length", violation.reason)
        assertEquals(10, violation.expectedLength)
        assertEquals(3, violation.actualLength)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = ExactLengthViolationFactory(length = 5)

        val violation = factory.createViolation(scope, "hi")

        assertIs<ExactLengthViolation>(violation)
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
        val factory = ExactLengthViolationFactory(length = 5)

        val violation = factory.createViolation(scope, "hi")

        assertIs<ExactLengthViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresLengthParameter() {
        val factory = ExactLengthViolationFactory(length = 8)

        assertEquals(8, factory.length)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresLengthParameter() {
        val rule = ExactLengthRule(length = 5)

        assertEquals(5, rule.length)
    }

    @Test
    fun ruleHasExactLengthCheckWithMatchingLength() {
        val rule = ExactLengthRule(length = 5)

        assertIs<ExactLengthCheck>(rule.validationCheck)
        assertEquals(5, (rule.validationCheck as ExactLengthCheck).length)
    }

    @Test
    fun ruleHasExactLengthViolationFactoryWithMatchingLength() {
        val rule = ExactLengthRule(length = 5)

        assertIs<ExactLengthViolationFactory>(rule.violationFactory)
        assertEquals(5, (rule.violationFactory as ExactLengthViolationFactory).length)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = ExactLengthViolationFactory(length = 5, reason = "custom")
        val rule = ExactLengthRule(length = 5, violationFactory = factory)

        assertEquals(factory, rule.violationFactory)
    }

    // endregion
}
