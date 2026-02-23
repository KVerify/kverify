package io.github.kverify.rule.set.string

import io.github.kverify.context.ListValidationContext
import io.github.kverify.context.ValidationPathElement
import io.github.kverify.rule.Rule
import io.github.kverify.scope.CollectingValidationScope
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class NotBlankRuleTest {
    // region Check tests

    @Test
    fun checkReturnsTrueForNonBlankString() {
        val scope = CollectingValidationScope()
        assertTrue(NotBlankCheck.isValid(scope, "hello"))
    }

    @Test
    fun checkReturnsTrueForSingleCharacter() {
        val scope = CollectingValidationScope()
        assertTrue(NotBlankCheck.isValid(scope, "a"))
    }

    @Test
    fun checkReturnsTrueForStringWithLeadingWhitespace() {
        val scope = CollectingValidationScope()
        assertTrue(NotBlankCheck.isValid(scope, " hello"))
    }

    @Test
    fun checkReturnsTrueForStringWithTrailingWhitespace() {
        val scope = CollectingValidationScope()
        assertTrue(NotBlankCheck.isValid(scope, "hello "))
    }

    @Test
    fun checkReturnsFalseForEmptyString() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, ""))
    }

    @Test
    fun checkReturnsFalseForSingleSpace() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, " "))
    }

    @Test
    fun checkReturnsFalseForMultipleSpaces() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, "   "))
    }

    @Test
    fun checkReturnsFalseForTabCharacter() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, "\t"))
    }

    @Test
    fun checkReturnsFalseForNewline() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, "\n"))
    }

    @Test
    fun checkReturnsFalseForMixedWhitespace() {
        val scope = CollectingValidationScope()
        assertFalse(NotBlankCheck.isValid(scope, " \t\n "))
    }

    // endregion

    // region ViolationFactory tests

    @Test
    fun factoryCreatesNotBlankViolationWithDefaultReason() {
        val scope = CollectingValidationScope()
        val factory = NotBlankViolationFactory()

        val violation = factory.createViolation(scope, "")

        assertIs<NotBlankViolation>(violation)
        assertEquals("Value must not be blank", violation.reason)
    }

    @Test
    fun factoryCreatesNotBlankViolationWithCustomReason() {
        val scope = CollectingValidationScope()
        val factory = NotBlankViolationFactory(reason = "Name is required")

        val violation = factory.createViolation(scope, "")

        assertIs<NotBlankViolation>(violation)
        assertEquals("Name is required", violation.reason)
    }

    @Test
    fun factoryCreatesViolationWithEmptyPathForDefaultScope() {
        val scope = CollectingValidationScope()
        val factory = NotBlankViolationFactory()

        val violation = factory.createViolation(scope, "")

        assertIs<NotBlankViolation>(violation)
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
        val factory = NotBlankViolationFactory()

        val violation = factory.createViolation(scope, "")

        assertIs<NotBlankViolation>(violation)
        assertEquals(
            listOf(ValidationPathElement.Property("field")),
            violation.validationPath,
        )
    }

    // endregion

    // region Rule tests

    @Test
    fun ruleHasNotBlankCheckAsValidationCheck() {
        val rule = NotBlankRule()

        assertSame(NotBlankCheck, rule.validationCheck)
    }

    @Test
    fun ruleHasNotBlankViolationFactoryByDefault() {
        val rule = NotBlankRule()

        assertIs<NotBlankViolationFactory>(rule.violationFactory)
    }

    @Test
    fun ruleWithCustomViolationFactory() {
        val factory = NotBlankViolationFactory(reason = "custom")
        val rule = NotBlankRule(factory)

        assertSame(factory, rule.violationFactory)
    }

    @Test
    fun companionImplementsRule() {
        assertIs<Rule<String>>(NotBlankRule.Companion)
    }

    @Test
    fun companionDelegatesCorrectly() {
        val scope = CollectingValidationScope()

        NotBlankRule.execute(scope, "hello")

        assertTrue(scope.build().isValid)
    }

    @Test
    fun companionDelegatesFailureCorrectly() {
        val scope = CollectingValidationScope()

        NotBlankRule.execute(scope, "")

        assertFalse(scope.build().isValid)
    }

    // endregion
}
