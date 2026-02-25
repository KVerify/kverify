package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ListValidationContext
import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class PatternRuleTest {
    private val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private val digitsOnlyRegex = Regex("^\\d+$")

    // region Check tests

    @Test
    fun checkReturnsTrueWhenValueMatchesPattern() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = digitsOnlyRegex)

        assertTrue(check.isValid(scope, "12345"))
    }

    @Test
    fun checkReturnsFalseWhenValueDoesNotMatchPattern() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = digitsOnlyRegex)

        assertFalse(check.isValid(scope, "abc"))
    }

    @Test
    fun checkReturnsFalseForPartialMatch() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = digitsOnlyRegex)

        assertFalse(check.isValid(scope, "123abc"))
    }

    @Test
    fun checkReturnsTrueForEmailPattern() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = emailRegex)

        assertTrue(check.isValid(scope, "user@example.com"))
    }

    @Test
    fun checkReturnsFalseForInvalidEmail() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = emailRegex)

        assertFalse(check.isValid(scope, "invalid-email"))
    }

    @Test
    fun checkReturnsTrueForEmptyStringWithMatchAllPattern() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = Regex("^.*$"))

        assertTrue(check.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForEmptyStringWithDigitsOnlyPattern() {
        val scope = CollectingValidationScope()
        val check = PatternCheck(regex = digitsOnlyRegex)

        assertFalse(check.isValid(scope, ""))
    }

    @Test
    fun checkStoresRegexParameter() {
        val check = PatternCheck(regex = digitsOnlyRegex)

        assertSame(digitsOnlyRegex, check.regex)
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesPatternViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val regex = digitsOnlyRegex
        val factory = PatternViolationFactory(regex = regex)

        val violation = factory.createViolation(scope, "abc")

        assertIs<PatternViolation>(violation)
        assertSame(regex, violation.regex)
        assertEquals("Value must match the pattern: $regex", violation.reason)
    }

    @Test
    fun factoryCreatesPatternViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = PatternViolationFactory(regex = emailRegex, reason = "Invalid email format")

        val violation = factory.createViolation(scope, "bad")

        assertIs<PatternViolation>(violation)
        assertEquals("Invalid email format", violation.reason)
        assertSame(emailRegex, violation.regex)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = PatternViolationFactory(regex = digitsOnlyRegex)

        val violation = factory.createViolation(scope, "abc")

        assertIs<PatternViolation>(violation)
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
        val factory = PatternViolationFactory(regex = digitsOnlyRegex)

        val violation = factory.createViolation(scope, "abc")

        assertIs<PatternViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    @Test
    fun factoryStoresRegexParameter() {
        val factory = PatternViolationFactory(regex = emailRegex)

        assertSame(emailRegex, factory.regex)
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleStoresRegexParameter() {
        val rule = PatternRule(regex = emailRegex)

        assertSame(emailRegex, rule.regex)
    }

    @Test
    fun ruleHasPatternCheckWithMatchingRegex() {
        val regex = emailRegex
        val rule = PatternRule(regex = regex)

        assertIs<PatternCheck>(rule.validationCheck)
        assertSame(regex, (rule.validationCheck as PatternCheck).regex)
    }

    @Test
    fun ruleHasPatternViolationFactoryWithMatchingRegex() {
        val regex = emailRegex
        val rule = PatternRule(regex = regex)

        assertIs<PatternViolationFactory>(rule.violationFactory)
        assertSame(regex, (rule.violationFactory as PatternViolationFactory).regex)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = PatternViolationFactory(regex = emailRegex, reason = "custom")
        val rule = PatternRule(regex = emailRegex, violationFactory = factory)

        assertSame(factory, rule.violationFactory)
    }

    // endregion
}
