package io.github.kverify.rule.set.provider

import io.github.kverify.util.MockStringCheckProvider
import io.github.kverify.util.MockStringViolationProvider
import io.github.kverify.util.assertName
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultStringRuleProviderTest {
    private val mockCheckProvider = MockStringCheckProvider()
    private val mockViolationProvider = MockStringViolationProvider()
    private val provider =
        DefaultStringRuleProvider(
            stringCheckProvider = mockCheckProvider,
            stringViolationProvider = mockViolationProvider,
        )
    private val value = "test"

    @Test
    fun alphabeticUsesCorrectCheck() {
        val name = "alphabetic"
        val rule = provider.alphabetic()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun alphanumericUsesCorrectCheck() {
        val name = "alphanumeric"
        val rule = provider.alphanumeric()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsAllUsesCorrectCheck() {
        val name = "containsAll"
        val rule = provider.containsAll(chars = listOf('a', 'b'))

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsNoneUsesCorrectCheck() {
        val name = "containsNone"
        val rule = provider.containsNone(chars = listOf('a', 'b'))

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsOnlyUsesCorrectCheck() {
        val name = "containsOnly"
        val rule = provider.containsOnly(chars = listOf('a', 'b'))

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsRegexUsesCorrectCheck() {
        val name = "containsRegex"
        val regex = Regex("[a-z]+")
        val rule = provider.containsRegex(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun containsUsesCorrectCheck() {
        val name = "contains"
        val rule = provider.contains(substring = "test", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun endsWithUsesCorrectCheck() {
        val name = "endsWith"
        val rule = provider.endsWith(suffix = "test", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun lengthBetweenUsesCorrectCheck() {
        val name = "lengthBetween"
        val rule = provider.lengthBetween(lengthRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun lengthNotBetweenUsesCorrectCheck() {
        val name = "lengthNotBetween"
        val rule = provider.lengthNotBetween(lengthRange = 1..10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun lowerCaseUsesCorrectCheck() {
        val name = "lowerCase"
        val rule = provider.lowerCase()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun matchesUsesCorrectCheck() {
        val name = "matches"
        val regex = Regex("[a-z]+")
        val rule = provider.matches(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun maxLengthUsesCorrectCheck() {
        val name = "maxLength"
        val rule = provider.maxLength(maxLength = 10)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun minLengthUsesCorrectCheck() {
        val name = "minLength"
        val rule = provider.minLength(minLength = 5)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notBlankUsesCorrectCheck() {
        val name = "notBlank"
        val rule = provider.notBlank()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notContainsUsesCorrectCheck() {
        val name = "notContains"
        val rule = provider.notContains(substring = "test", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notContainsRegexUsesCorrectCheck() {
        val name = "notContainsRegex"
        val regex = Regex("[a-z]+")
        val rule = provider.notContainsRegex(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notEmptyUsesCorrectCheck() {
        val name = "notEmpty"
        val rule = provider.notEmpty()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notMatchesUsesCorrectCheck() {
        val name = "notMatches"
        val regex = Regex("[a-z]+")
        val rule = provider.notMatches(regex = regex)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun notOfLengthUsesCorrectCheck() {
        val name = "notOfLength"
        val rule = provider.notOfLength(length = 5)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun numericUsesCorrectCheck() {
        val name = "numeric"
        val rule = provider.numeric()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun ofLengthUsesCorrectCheck() {
        val name = "ofLength"
        val rule = provider.ofLength(length = 5)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun startsWithUsesCorrectCheck() {
        val name = "startsWith"
        val rule = provider.startsWith(prefix = "test", ignoreCase = false)

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }

    @Test
    fun upperCaseUsesCorrectCheck() {
        val name = "upperCase"
        val rule = provider.upperCase()

        val reason =
            rule
                .violationFactory
                .createViolation(value)
                .reason

        assertName(rule.validationCheck, name)
        assertEquals(reason, name)
    }
}
