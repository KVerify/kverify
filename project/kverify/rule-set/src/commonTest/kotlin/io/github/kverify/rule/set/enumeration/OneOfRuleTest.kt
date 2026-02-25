package io.github.kverify.rule.set.enumeration

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue

class OneOfRuleTest {
    private val allowed = setOf("a", "b", "c")

    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueIsInAllowed() {
        val scope = CollectingValidationScope()
        val check = OneOfCheck(allowed)

        assertTrue(check.isValid(scope, "a"))
    }

    @Test
    fun checkReturnsTrueForEachAllowedValue() {
        val scope = CollectingValidationScope()
        val check = OneOfCheck(allowed)

        assertTrue(check.isValid(scope, "a"))
        assertTrue(check.isValid(scope, "b"))
        assertTrue(check.isValid(scope, "c"))
    }

    @Test
    fun checkReturnsFalseWhenValueIsNotInAllowed() {
        val scope = CollectingValidationScope()
        val check = OneOfCheck(allowed)

        assertFalse(check.isValid(scope, "d"))
    }

    @Test
    fun checkReturnsFalseForEmptyString() {
        val scope = CollectingValidationScope()
        val check = OneOfCheck(allowed)

        assertFalse(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseWhenAllowedSetIsEmpty() {
        val scope = CollectingValidationScope()
        val check = OneOfCheck(emptySet<String>())

        assertFalse(check.isValid(scope, "a"))
    }

    @Test
    fun checkStoresAllowedParameter() {
        val check = OneOfCheck(allowed)

        assertEquals(allowed, check.allowed)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesOneOfViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = OneOfViolationFactory(allowed)

        val violation = factory.createViolation(scope, "d")

        assertIs<OneOfViolation<String>>(violation)
        assertEquals(allowed, violation.allowed)
        assertEquals("d", violation.actual)
        assertEquals("Value must be one of $allowed. Actual: d", violation.reason)
    }

    @Test
    fun factoryCreatesOneOfViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = OneOfViolationFactory(allowed, reason = "Invalid choice")

        val violation = factory.createViolation(scope, "d")

        assertIs<OneOfViolation<String>>(violation)
        assertEquals("Invalid choice", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = OneOfViolationFactory(allowed)

        val violation = factory.createViolation(scope, "d")

        assertIs<OneOfViolation<String>>(violation)
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
        val factory = OneOfViolationFactory(allowed)

        val violation = factory.createViolation(scope, "d")

        assertIs<OneOfViolation<String>>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresAllowedParameter() {
        val rule = OneOfRule(allowed)

        assertEquals(allowed, rule.allowed)
    }

    @Test
    fun ruleHasOneOfCheckWithMatchingAllowed() {
        val rule = OneOfRule(allowed)

        val check = rule.validationCheck
        assertIs<OneOfCheck<String>>(check)
        assertEquals(allowed, check.allowed)
    }

    @Test
    fun ruleHasOneOfViolationFactoryByDefault() {
        val rule = OneOfRule(allowed)

        val factory = rule.violationFactory
        assertIs<OneOfViolationFactory<String>>(factory)
        assertEquals(allowed, factory.allowed)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = OneOfViolationFactory(allowed, reason = "custom")
        val rule = OneOfRule(allowed, violationFactory = factory)

        assertIs<OneOfViolationFactory<String>>(rule.violationFactory)
    }

    // endregion
}
