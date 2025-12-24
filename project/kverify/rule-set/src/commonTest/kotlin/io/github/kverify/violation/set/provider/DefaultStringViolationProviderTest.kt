package io.github.kverify.violation.set.provider

import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultStringViolationProviderTest {
    private val localizationProvider = object : StringViolationLocalizationProvider {
        override fun alphabetic(value: String, name: String?): String = "alphabetic: $name"
        override fun alphanumeric(value: String, name: String?): String = "alphanumeric: $name"
        override fun containsAll(value: String, chars: Iterable<Char>, name: String?): String = "containsAll: $name"
        override fun containsNone(value: String, chars: Iterable<Char>, name: String?): String = "containsNone: $name"
        override fun containsOnly(value: String, chars: Iterable<Char>, name: String?): String = "containsOnly: $name"
        override fun containsRegex(value: String, regex: Regex, name: String?): String = "containsRegex: $name"
        override fun contains(value: String, substring: String, ignoreCase: Boolean, name: String?): String =
            "contains: $name, ignoreCase=$ignoreCase"
        override fun endsWith(value: String, suffix: String, ignoreCase: Boolean, name: String?): String =
            "endsWith: $name, ignoreCase=$ignoreCase"
        override fun lengthBetween(value: String, lengthRange: IntRange, name: String?): String = "lengthBetween: $name"
        override fun lengthNotBetween(value: String, lengthRange: IntRange, name: String?): String = "lengthNotBetween: $name"
        override fun lowerCase(value: String, name: String?): String = "lowerCase: $name"
        override fun matches(value: String, regex: Regex, name: String?): String = "matches: $name"
        override fun maxLength(value: String, maxLength: Int, name: String?): String = "maxLength: $name"
        override fun minLength(value: String, minLength: Int, name: String?): String = "minLength: $name"
        override fun notBlank(value: String, name: String?): String = "notBlank: $name"
        override fun notContains(value: String, substring: String, ignoreCase: Boolean, name: String?): String =
            "notContains: $name, ignoreCase=$ignoreCase"
        override fun notContainsRegex(value: String, regex: Regex, name: String?): String = "notContainsRegex: $name"
        override fun notEmpty(value: String, name: String?): String = "notEmpty: $name"
        override fun notMatches(value: String, regex: Regex, name: String?): String = "notMatches: $name"
        override fun notOfLength(value: String, length: Int, name: String?): String = "notOfLength: $name"
        override fun numeric(value: String, name: String?): String = "numeric: $name"
        override fun ofLength(value: String, length: Int, name: String?): String = "ofLength: $name"
        override fun startsWith(value: String, prefix: String, ignoreCase: Boolean, name: String?): String =
            "startsWith: $name, ignoreCase=$ignoreCase"
        override fun upperCase(value: String, name: String?): String = "upperCase: $name"
    }

    private val provider = DefaultStringViolationProvider(localizationProvider)

    @Test
    fun alphabeticDelegatesToLocalizationProvider() {
        val violation = provider.alphabetic("test123", "username")
        assertEquals("alphabetic: username", violation.reason)
    }

    @Test
    fun alphabeticWithNullName() {
        val violation = provider.alphabetic("hello!", null)
        assertEquals("alphabetic: null", violation.reason)
    }

    @Test
    fun alphanumericDelegatesToLocalizationProvider() {
        val violation = provider.alphanumeric("test!", "password")
        assertEquals("alphanumeric: password", violation.reason)
    }

    @Test
    fun alphanumericWithNullName() {
        val violation = provider.alphanumeric("hello world", null)
        assertEquals("alphanumeric: null", violation.reason)
    }

    @Test
    fun containsAllDelegatesToLocalizationProvider() {
        val violation = provider.containsAll("hello", listOf('x', 'y'), "text")
        assertEquals("containsAll: text", violation.reason)
    }

    @Test
    fun containsAllWithNullName() {
        val violation = provider.containsAll("test", listOf('a', 'b'), null)
        assertEquals("containsAll: null", violation.reason)
    }

    @Test
    fun containsNoneDelegatesToLocalizationProvider() {
        val violation = provider.containsNone("hello", listOf('e', 'l'), "greeting")
        assertEquals("containsNone: greeting", violation.reason)
    }

    @Test
    fun containsNoneWithNullName() {
        val violation = provider.containsNone("test", listOf('t'), null)
        assertEquals("containsNone: null", violation.reason)
    }

    @Test
    fun containsOnlyDelegatesToLocalizationProvider() {
        val violation = provider.containsOnly("hello", listOf('h', 'e', 'l'), "word")
        assertEquals("containsOnly: word", violation.reason)
    }

    @Test
    fun containsOnlyWithNullName() {
        val violation = provider.containsOnly("abc", listOf('a', 'b'), null)
        assertEquals("containsOnly: null", violation.reason)
    }

    @Test
    fun containsRegexDelegatesToLocalizationProvider() {
        val violation = provider.containsRegex("hello", Regex("[0-9]+"), "text")
        assertEquals("containsRegex: text", violation.reason)
    }

    @Test
    fun containsRegexWithNullName() {
        val violation = provider.containsRegex("test", Regex("\\d+"), null)
        assertEquals("containsRegex: null", violation.reason)
    }

    @Test
    fun containsDelegatesToLocalizationProviderWithIgnoreCase() {
        val violation = provider.contains("hello", "WORLD", ignoreCase = true, "greeting")
        assertEquals("contains: greeting, ignoreCase=true", violation.reason)
    }

    @Test
    fun containsWithNullNameAndCaseSensitive() {
        val violation = provider.contains("test", "TEST", ignoreCase = false, null)
        assertEquals("contains: null, ignoreCase=false", violation.reason)
    }

    @Test
    fun endsWithDelegatesToLocalizationProviderWithIgnoreCase() {
        val violation = provider.endsWith("testing", "ING", ignoreCase = true, "word")
        assertEquals("endsWith: word, ignoreCase=true", violation.reason)
    }

    @Test
    fun endsWithWithNullNameAndCaseSensitive() {
        val violation = provider.endsWith("hello", "WORLD", ignoreCase = false, null)
        assertEquals("endsWith: null, ignoreCase=false", violation.reason)
    }

    @Test
    fun lengthBetweenDelegatesToLocalizationProvider() {
        val violation = provider.lengthBetween("hi", 5..10, "password")
        assertEquals("lengthBetween: password", violation.reason)
    }

    @Test
    fun lengthBetweenWithNullName() {
        val violation = provider.lengthBetween("verylongstring", 1..5, null)
        assertEquals("lengthBetween: null", violation.reason)
    }

    @Test
    fun lengthNotBetweenDelegatesToLocalizationProvider() {
        val violation = provider.lengthNotBetween("hello", 3..7, "word")
        assertEquals("lengthNotBetween: word", violation.reason)
    }

    @Test
    fun lengthNotBetweenWithNullName() {
        val violation = provider.lengthNotBetween("test", 2..6, null)
        assertEquals("lengthNotBetween: null", violation.reason)
    }

    @Test
    fun lowerCaseDelegatesToLocalizationProvider() {
        val violation = provider.lowerCase("Hello", "word")
        assertEquals("lowerCase: word", violation.reason)
    }

    @Test
    fun lowerCaseWithNullName() {
        val violation = provider.lowerCase("TeSt", null)
        assertEquals("lowerCase: null", violation.reason)
    }

    @Test
    fun matchesDelegatesToLocalizationProvider() {
        val violation = provider.matches("hello123", Regex("^[a-z]+$"), "username")
        assertEquals("matches: username", violation.reason)
    }

    @Test
    fun matchesWithNullName() {
        val violation = provider.matches("test", Regex("\\d+"), null)
        assertEquals("matches: null", violation.reason)
    }

    @Test
    fun maxLengthDelegatesToLocalizationProvider() {
        val violation = provider.maxLength("toolong", 5, "password")
        assertEquals("maxLength: password", violation.reason)
    }

    @Test
    fun maxLengthWithNullName() {
        val violation = provider.maxLength("verylongstring", 10, null)
        assertEquals("maxLength: null", violation.reason)
    }

    @Test
    fun minLengthDelegatesToLocalizationProvider() {
        val violation = provider.minLength("hi", 5, "password")
        assertEquals("minLength: password", violation.reason)
    }

    @Test
    fun minLengthWithNullName() {
        val violation = provider.minLength("ab", 10, null)
        assertEquals("minLength: null", violation.reason)
    }

    @Test
    fun notBlankDelegatesToLocalizationProvider() {
        val violation = provider.notBlank("   ", "input")
        assertEquals("notBlank: input", violation.reason)
    }

    @Test
    fun notBlankWithNullName() {
        val violation = provider.notBlank("  ", null)
        assertEquals("notBlank: null", violation.reason)
    }

    @Test
    fun notContainsDelegatesToLocalizationProviderWithIgnoreCase() {
        val violation = provider.notContains("hello world", "world", ignoreCase = true, "greeting")
        assertEquals("notContains: greeting, ignoreCase=true", violation.reason)
    }

    @Test
    fun notContainsWithNullNameAndCaseSensitive() {
        val violation = provider.notContains("test", "TEST", ignoreCase = false, null)
        assertEquals("notContains: null, ignoreCase=false", violation.reason)
    }

    @Test
    fun notContainsRegexDelegatesToLocalizationProvider() {
        val violation = provider.notContainsRegex("hello123", Regex("[0-9]+"), "text")
        assertEquals("notContainsRegex: text", violation.reason)
    }

    @Test
    fun notContainsRegexWithNullName() {
        val violation = provider.notContainsRegex("test123", Regex("\\d+"), null)
        assertEquals("notContainsRegex: null", violation.reason)
    }

    @Test
    fun notEmptyDelegatesToLocalizationProvider() {
        val violation = provider.notEmpty("", "username")
        assertEquals("notEmpty: username", violation.reason)
    }

    @Test
    fun notEmptyWithNullName() {
        val violation = provider.notEmpty("", null)
        assertEquals("notEmpty: null", violation.reason)
    }

    @Test
    fun notMatchesDelegatesToLocalizationProvider() {
        val violation = provider.notMatches("hello", Regex("^[a-z]+$"), "value")
        assertEquals("notMatches: value", violation.reason)
    }

    @Test
    fun notMatchesWithNullName() {
        val violation = provider.notMatches("123", Regex("\\d+"), null)
        assertEquals("notMatches: null", violation.reason)
    }

    @Test
    fun notOfLengthDelegatesToLocalizationProvider() {
        val violation = provider.notOfLength("test", 4, "word")
        assertEquals("notOfLength: word", violation.reason)
    }

    @Test
    fun notOfLengthWithNullName() {
        val violation = provider.notOfLength("hello", 5, null)
        assertEquals("notOfLength: null", violation.reason)
    }

    @Test
    fun numericDelegatesToLocalizationProvider() {
        val violation = provider.numeric("123abc", "code")
        assertEquals("numeric: code", violation.reason)
    }

    @Test
    fun numericWithNullName() {
        val violation = provider.numeric("12.34", null)
        assertEquals("numeric: null", violation.reason)
    }

    @Test
    fun ofLengthDelegatesToLocalizationProvider() {
        val violation = provider.ofLength("hi", 5, "password")
        assertEquals("ofLength: password", violation.reason)
    }

    @Test
    fun ofLengthWithNullName() {
        val violation = provider.ofLength("test", 10, null)
        assertEquals("ofLength: null", violation.reason)
    }

    @Test
    fun startsWithDelegatesToLocalizationProviderWithIgnoreCase() {
        val violation = provider.startsWith("hello", "HELLO", ignoreCase = true, "greeting")
        assertEquals("startsWith: greeting, ignoreCase=true", violation.reason)
    }

    @Test
    fun startsWithWithNullNameAndCaseSensitive() {
        val violation = provider.startsWith("test", "WORLD", ignoreCase = false, null)
        assertEquals("startsWith: null, ignoreCase=false", violation.reason)
    }

    @Test
    fun upperCaseDelegatesToLocalizationProvider() {
        val violation = provider.upperCase("HELLo", "word")
        assertEquals("upperCase: word", violation.reason)
    }

    @Test
    fun upperCaseWithNullName() {
        val violation = provider.upperCase("TeST", null)
        assertEquals("upperCase: null", violation.reason)
    }

    @Test
    fun usesDefaultLocalizationProviderWhenNotSpecified() {
        val defaultProvider = DefaultStringViolationProvider()
        val violation = defaultProvider.notEmpty("", "username")
        assertTrue(violation.reason.contains("username"))
    }
}
