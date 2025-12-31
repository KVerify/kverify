package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.model.NamedValue
import io.github.kverify.util.MockStringCheckProvider
import io.github.kverify.util.MockStringViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultNamedStringRuleProviderTest {
    private val mockCheckProvider = MockStringCheckProvider()
    private val mockViolationProvider = MockStringViolationProvider()
    private val provider =
        DefaultNamedStringRuleProvider(
            stringCheckProvider = mockCheckProvider,
            stringViolationProvider = mockViolationProvider,
        )
    private val namedValue = NamedValue("myString", "test")

    @Test
    fun namedAlphabeticUsesCorrectCheck() {
        val name = "alphabetic"
        val rule = provider.namedAlphabetic()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedAlphanumericUsesCorrectCheck() {
        val name = "alphanumeric"
        val rule = provider.namedAlphanumeric()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsAllUsesCorrectCheck() {
        val name = "containsAll"
        val rule = provider.namedContainsAll(chars = listOf('a', 'b'))

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsNoneUsesCorrectCheck() {
        val name = "containsNone"
        val rule = provider.namedContainsNone(chars = listOf('x', 'y'))

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsOnlyUsesCorrectCheck() {
        val name = "containsOnly"
        val rule = provider.namedContainsOnly(chars = listOf('a', 'b', 'c'))

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsRegexUsesCorrectCheck() {
        val name = "containsRegex"
        val regex = Regex("[a-z]+")
        val rule = provider.namedContainsRegex(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedContainsUsesCorrectCheck() {
        val name = "contains"
        val rule = provider.namedContains(substring = "test", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedEndsWithUsesCorrectCheck() {
        val name = "endsWith"
        val rule = provider.namedEndsWith(suffix = "st", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedLengthBetweenUsesCorrectCheck() {
        val name = "lengthBetween"
        val rule = provider.namedLengthBetween(lengthRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedLengthNotBetweenUsesCorrectCheck() {
        val name = "lengthNotBetween"
        val rule = provider.namedLengthNotBetween(lengthRange = 10..20)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedLowerCaseUsesCorrectCheck() {
        val name = "lowerCase"
        val rule = provider.namedLowerCase()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedMatchesUsesCorrectCheck() {
        val name = "matches"
        val regex = Regex("[a-z]+")
        val rule = provider.namedMatches(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedMaxLengthUsesCorrectCheck() {
        val name = "maxLength"
        val rule = provider.namedMaxLength(maxLength = 10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedMinLengthUsesCorrectCheck() {
        val name = "minLength"
        val rule = provider.namedMinLength(minLength = 1)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotBlankUsesCorrectCheck() {
        val name = "notBlank"
        val rule = provider.namedNotBlank()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotContainsUsesCorrectCheck() {
        val name = "notContains"
        val rule = provider.namedNotContains(substring = "xyz", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotContainsRegexUsesCorrectCheck() {
        val name = "notContainsRegex"
        val regex = Regex("[0-9]+")
        val rule = provider.namedNotContainsRegex(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotEmptyUsesCorrectCheck() {
        val name = "notEmpty"
        val rule = provider.namedNotEmpty()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotMatchesUsesCorrectCheck() {
        val name = "notMatches"
        val regex = Regex("[0-9]+")
        val rule = provider.namedNotMatches(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNotOfLengthUsesCorrectCheck() {
        val name = "notOfLength"
        val rule = provider.namedNotOfLength(length = 10)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedNumericUsesCorrectCheck() {
        val name = "numeric"
        val rule = provider.namedNumeric()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedOfLengthUsesCorrectCheck() {
        val name = "ofLength"
        val rule = provider.namedOfLength(length = 4)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedStartsWithUsesCorrectCheck() {
        val name = "startsWith"
        val rule = provider.namedStartsWith(prefix = "te", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun namedUpperCaseUsesCorrectCheck() {
        val name = "upperCase"
        val rule = provider.namedUpperCase()

        val reason =
            rule
                .violationFactory
                .createViolation(namedValue)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
