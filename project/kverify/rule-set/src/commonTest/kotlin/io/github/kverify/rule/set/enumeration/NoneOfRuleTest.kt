package io.github.kverify.rule.set.enumeration

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class NoneOfRuleTest {
    private val forbidden = setOf("x", "y", "z")

    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueIsNotInForbidden() {
        val scope = CollectingValidationScope()
        val check = NoneOfCheck(forbidden)

        assertTrue(check.isValid(scope, "a"))
    }

    @Test
    fun checkReturnsTrueForEmptyString() {
        val scope = CollectingValidationScope()
        val check = NoneOfCheck(forbidden)

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseWhenValueIsInForbidden() {
        val scope = CollectingValidationScope()
        val check = NoneOfCheck(forbidden)

        assertFalse(check.isValid(scope, "x"))
    }

    @Test
    fun checkReturnsFalseForEachForbiddenValue() {
        val scope = CollectingValidationScope()
        val check = NoneOfCheck(forbidden)

        assertFalse(check.isValid(scope, "x"))
        assertFalse(check.isValid(scope, "y"))
        assertFalse(check.isValid(scope, "z"))
    }

    @Test
    fun checkReturnsTrueWhenForbiddenSetIsEmpty() {
        val scope = CollectingValidationScope()
        val check = NoneOfCheck(emptySet<String>())

        assertTrue(check.isValid(scope, "anything"))
    }

    @Test
    fun checkStoresForbiddenParameter() {
        val check = NoneOfCheck(forbidden)

        assertEquals(forbidden, check.forbidden)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesNoneOfViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = NoneOfViolationFactory(forbidden)

        val violation = factory.createViolation(scope, "x")

        assertIs<NoneOfViolation<String>>(violation)
        assertEquals(forbidden, violation.forbidden)
        assertEquals("x", violation.actual)
        assertEquals("Value must not be one of $forbidden. Actual: x", violation.reason)
    }

    @Test
    fun factoryCreatesNoneOfViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = NoneOfViolationFactory(forbidden, reason = "Forbidden value used")

        val violation = factory.createViolation(scope, "x")

        assertIs<NoneOfViolation<String>>(violation)
        assertEquals("Forbidden value used", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = NoneOfViolationFactory(forbidden)

        val violation = factory.createViolation(scope, "x")

        assertIs<NoneOfViolation<String>>(violation)
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
        val factory = NoneOfViolationFactory(forbidden)

        val violation = factory.createViolation(scope, "x")

        assertIs<NoneOfViolation<String>>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresForbiddenParameter() {
        val rule = NoneOfRule(forbidden)

        assertEquals(forbidden, rule.forbidden)
    }

    @Test
    fun ruleHasNoneOfCheckWithMatchingForbidden() {
        val rule = NoneOfRule(forbidden)

        val check = rule.validationCheck
        assertIs<NoneOfCheck<String>>(check)
        assertEquals(forbidden, check.forbidden)
    }

    @Test
    fun ruleHasNoneOfViolationFactoryByDefault() {
        val rule = NoneOfRule(forbidden)

        val factory = rule.violationFactory
        assertIs<NoneOfViolationFactory<String>>(factory)
        assertEquals(forbidden, factory.forbidden)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = NoneOfViolationFactory(forbidden, reason = "custom")
        val rule = NoneOfRule(forbidden, violationFactory = factory)

        assertIs<NoneOfViolationFactory<String>>(rule.violationFactory)
    }

    // endregion
}
