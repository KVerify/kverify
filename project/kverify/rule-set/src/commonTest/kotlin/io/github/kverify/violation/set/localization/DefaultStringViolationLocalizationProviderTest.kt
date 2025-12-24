package io.github.kverify.violation.set.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultStringViolationLocalizationProviderTest {
    private val provider = DefaultStringViolationLocalizationProvider

    @Test
    fun alphabeticWithCustomName() {
        val result = provider.alphabetic("test123", "username")
        assertEquals("'username' must contain only letters, but it also contains: [1, 2, 3].", result)
    }

    @Test
    fun alphabeticWithNullName() {
        val result = provider.alphabetic("hello!", null)
        assertEquals("'string' must contain only letters, but it also contains: [!].", result)
    }

    @Test
    fun alphabeticWithSpecialChars() {
        val result = provider.alphabetic("a@b#c", "text")
        assertTrue(result.contains("'text'"))
        assertTrue(result.contains("[@, #]") || result.contains("[#, @]"))
    }

    @Test
    fun alphanumericWithCustomName() {
        val result = provider.alphanumeric("test123!", "password")
        assertEquals("'password' must contain only numbers and letters, but it also contains: [!].", result)
    }

    @Test
    fun alphanumericWithNullName() {
        val result = provider.alphanumeric("hello world", null)
        assertEquals("'string' must contain only numbers and letters, but it also contains: [ ].", result)
    }

    @Test
    fun alphanumericWithMultipleSpecialChars() {
        val result = provider.alphanumeric("a@b#c$", "value")
        assertTrue(result.contains("'value'"))
        assertTrue(result.contains("@"))
        assertTrue(result.contains("#"))
        assertTrue(result.contains("$"))
    }

    @Test
    fun containsAllWithCustomName() {
        val result = provider.containsAll("hello", listOf('a', 'b', 'c'), "text")
        assertTrue(result.contains("'text'"))
        assertTrue(result.contains("must contain all of the following characters"))
    }

    @Test
    fun containsAllWithNullName() {
        val result = provider.containsAll("test", listOf('x', 'y', 'z'), null)
        assertTrue(result.contains("'string'"))
        assertTrue(result.contains("[x, y, z]"))
    }

    @Test
    fun containsNoneWithCustomName() {
        val result = provider.containsNone("hello", listOf('e', 'l'), "greeting")
        assertTrue(result.contains("'greeting'"))
        assertTrue(result.contains("must not contain any of the following characters"))
        assertTrue(result.contains("these are present"))
    }

    @Test
    fun containsNoneWithNullName() {
        val result = provider.containsNone("test", listOf('t'), null)
        assertTrue(result.contains("'comparable'"))
        assertTrue(result.contains("[t]"))
    }

    @Test
    fun containsOnlyWithCustomName() {
        val result = provider.containsOnly("hello", listOf('h', 'e', 'l'), "word")
        assertEquals("'word' must contain only the following characters: [h, e, l], but it also contains: [o].", result)
    }

    @Test
    fun containsOnlyWithNullName() {
        val result = provider.containsOnly("abc", listOf('a', 'b'), null)
        assertEquals("'string' must contain only the following characters: [a, b], but it also contains: [c].", result)
    }

    @Test
    fun containsOnlyWithMultipleUnexpectedChars() {
        val result = provider.containsOnly("abcdef", listOf('a', 'b'), "letters")
        assertTrue(result.contains("'letters'"))
        assertTrue(result.contains("[c, d, e, f]"))
    }

    @Test
    fun containsRegexWithCustomName() {
        val result = provider.containsRegex("hello", Regex("[0-9]+"), "text")
        assertEquals("'text' must contain the following regex pattern: '[0-9]+', but it does not.", result)
    }

    @Test
    fun containsRegexWithNullName() {
        val result = provider.containsRegex("test", Regex("\\d{3}"), null)
        assertEquals("'comparable' must contain the following regex pattern: '\\d{3}', but it does not.", result)
    }

    @Test
    fun containsWithCustomNameCaseSensitive() {
        val result = provider.contains("hello", "WORLD", ignoreCase = false, "greeting")
        assertEquals("'greeting' must contain the following substring (ignoreCase=false): 'WORLD', but it does not.", result)
    }

    @Test
    fun containsWithNullNameIgnoreCase() {
        val result = provider.contains("test", "TEST", ignoreCase = true, null)
        assertEquals("'string' must contain the following substring (ignoreCase=true): 'TEST', but it does not.", result)
    }

    @Test
    fun endsWithWithCustomNameCaseSensitive() {
        val result = provider.endsWith("testing", "ING", ignoreCase = false, "word")
        assertEquals("'word' must end with the following suffix (ignoreCase=false): ING, but it does not.", result)
    }

    @Test
    fun endsWithWithNullNameIgnoreCase() {
        val result = provider.endsWith("hello", "WORLD", ignoreCase = true, null)
        assertEquals("'string' must end with the following suffix (ignoreCase=true): WORLD, but it does not.", result)
    }

    @Test
    fun lengthBetweenWithCustomName() {
        val result = provider.lengthBetween("hi", 5..10, "password")
        assertEquals("'password' must have a length between 5 and 10 (inclusive), but its length is 2.", result)
    }

    @Test
    fun lengthBetweenWithNullName() {
        val result = provider.lengthBetween("verylongstring", 1..5, null)
        assertEquals("'string' must have a length between 1 and 5 (inclusive), but its length is 14.", result)
    }

    @Test
    fun lengthBetweenBelowRange() {
        val result = provider.lengthBetween("ab", 5..10, "text")
        assertEquals("'text' must have a length between 5 and 10 (inclusive), but its length is 2.", result)
    }

    @Test
    fun lengthBetweenAboveRange() {
        val result = provider.lengthBetween("verylongtext", 1..5, "input")
        assertEquals("'input' must have a length between 1 and 5 (inclusive), but its length is 12.", result)
    }

    @Test
    fun lengthNotBetweenWithCustomName() {
        val result = provider.lengthNotBetween("hello", 3..7, "word")
        assertEquals("'word' must NOT have a length between 3 and 7 (inclusive), but its length is 5.", result)
    }

    @Test
    fun lengthNotBetweenWithNullName() {
        val result = provider.lengthNotBetween("test", 2..6, null)
        assertEquals("'string' must NOT have a length between 2 and 6 (inclusive), but its length is 4.", result)
    }

    @Test
    fun lengthNotBetweenAtLowerBound() {
        val result = provider.lengthNotBetween("abc", 3..5, "value")
        assertEquals("'value' must NOT have a length between 3 and 5 (inclusive), but its length is 3.", result)
    }

    @Test
    fun lengthNotBetweenAtUpperBound() {
        val result = provider.lengthNotBetween("hello", 3..5, "text")
        assertEquals("'text' must NOT have a length between 3 and 5 (inclusive), but its length is 5.", result)
    }

    @Test
    fun lowerCaseWithCustomName() {
        val result = provider.lowerCase("Hello", "word")
        assertEquals("'word' must be entirely lower case, but it also contains upper case characters: [H].", result)
    }

    @Test
    fun lowerCaseWithNullName() {
        val result = provider.lowerCase("TeSt", null)
        assertTrue(result.contains("'string'"))
        assertTrue(result.contains("upper case characters"))
    }

    @Test
    fun lowerCaseWithMultipleUpperCase() {
        val result = provider.lowerCase("HeLLo", "greeting")
        assertTrue(result.contains("'greeting'"))
        assertTrue(result.contains("[H, L, L]"))
    }

    @Test
    fun matchesWithCustomName() {
        val result = provider.matches("hello123", Regex("^[a-z]+$"), "username")
        assertEquals("'username' must match the regular expression: '^[a-z]+$'.", result)
    }

    @Test
    fun matchesWithNullName() {
        val result = provider.matches("test", Regex("\\d+"), null)
        assertEquals("'string' must match the regular expression: '\\d+'.", result)
    }

    @Test
    fun maxLengthWithCustomName() {
        val result = provider.maxLength("toolong", 5, "password")
        assertEquals("'password' length must be 5 at most, but it is 7.", result)
    }

    @Test
    fun maxLengthWithNullName() {
        val result = provider.maxLength("verylongstring", 10, null)
        assertEquals("'string' length must be 10 at most, but it is 14.", result)
    }

    @Test
    fun minLengthWithCustomName() {
        val result = provider.minLength("hi", 5, "password")
        assertEquals("'password' length must be at least 5, but it is 2.", result)
    }

    @Test
    fun minLengthWithNullName() {
        val result = provider.minLength("ab", 10, null)
        assertEquals("'string' length must be at least 10, but it is 2.", result)
    }

    @Test
    fun minLengthWithEmptyString() {
        val result = provider.minLength("", 1, "value")
        assertEquals("'value' length must be at least 1, but it is 0.", result)
    }

    @Test
    fun notBlankWithCustomName() {
        val result = provider.notBlank("   ", "input")
        assertEquals("'input' must not be blank.", result)
    }

    @Test
    fun notBlankWithNullName() {
        val result = provider.notBlank("  ", null)
        assertEquals("'string' must not be blank.", result)
    }

    @Test
    fun notContainsWithCustomNameCaseSensitive() {
        val result = provider.notContains("hello world", "world", ignoreCase = false, "greeting")
        assertEquals("'greeting' must NOT contain the following substring (ignoreCase=false): 'world', but it does.", result)
    }

    @Test
    fun notContainsWithNullNameIgnoreCase() {
        val result = provider.notContains("test", "TEST", ignoreCase = true, null)
        assertEquals("'string' must NOT contain the following substring (ignoreCase=true): 'TEST', but it does.", result)
    }

    @Test
    fun notContainsRegexWithCustomName() {
        val result = provider.notContainsRegex("hello123", Regex("[0-9]+"), "text")
        assertEquals("'text' must NOT contain the following regex pattern: '[0-9]+', but it does.", result)
    }

    @Test
    fun notContainsRegexWithNullName() {
        val result = provider.notContainsRegex("test123", Regex("\\d+"), null)
        assertEquals("'string' must NOT contain the following regex pattern: '\\d+', but it does.", result)
    }

    @Test
    fun notEmptyWithCustomName() {
        val result = provider.notEmpty("", "username")
        assertEquals("'username' must not be empty.", result)
    }

    @Test
    fun notEmptyWithNullName() {
        val result = provider.notEmpty("", null)
        assertEquals("'string' must not be empty.", result)
    }

    @Test
    fun notMatchesWithCustomName() {
        val result = provider.notMatches("hello", Regex("^[a-z]+$"), "value")
        assertEquals("'value' must not match the regex pattern: '^[a-z]+$'.", result)
    }

    @Test
    fun notMatchesWithNullName() {
        val result = provider.notMatches("123", Regex("\\d+"), null)
        assertEquals("'string' must not match the regex pattern: '\\d+'.", result)
    }

    @Test
    fun notOfLengthWithCustomName() {
        val result = provider.notOfLength("test", 4, "word")
        assertEquals("'word' must NOT be of length 4, but it is.", result)
    }

    @Test
    fun notOfLengthWithNullName() {
        val result = provider.notOfLength("hello", 5, null)
        assertEquals("'string' must NOT be of length 5, but it is.", result)
    }

    @Test
    fun numericWithCustomName() {
        val result = provider.numeric("123abc", "code")
        assertEquals("'code' must contain only digits, but it also contains: [a, b, c]", result)
    }

    @Test
    fun numericWithNullName() {
        val result = provider.numeric("12.34", null)
        assertEquals("'string' must contain only digits, but it also contains: [.]", result)
    }

    @Test
    fun numericWithSpecialChars() {
        val result = provider.numeric("123!", "number")
        assertEquals("'number' must contain only digits, but it also contains: [!]", result)
    }

    @Test
    fun ofLengthWithCustomName() {
        val result = provider.ofLength("hi", 5, "password")
        assertEquals("'password' length must be exactly 5, but it is 2.", result)
    }

    @Test
    fun ofLengthWithNullName() {
        val result = provider.ofLength("test", 10, null)
        assertEquals("'string' length must be exactly 10, but it is 4.", result)
    }

    @Test
    fun ofLengthTooLong() {
        val result = provider.ofLength("verylongstring", 5, "input")
        assertEquals("'input' length must be exactly 5, but it is 14.", result)
    }

    @Test
    fun startsWithWithCustomNameCaseSensitive() {
        val result = provider.startsWith("hello", "HELLO", ignoreCase = false, "greeting")
        assertEquals("'greeting' must start with the following prefix (ignoreCase=false): 'HELLO', but it does not.", result)
    }

    @Test
    fun startsWithWithNullNameIgnoreCase() {
        val result = provider.startsWith("test", "WORLD", ignoreCase = true, null)
        assertEquals("'string' must start with the following prefix (ignoreCase=true): 'WORLD', but it does not.", result)
    }

    @Test
    fun upperCaseWithCustomName() {
        val result = provider.upperCase("HELLo", "word")
        assertEquals("'word' must be entirely upper case, but it also contains lowercase characters: [o].", result)
    }

    @Test
    fun upperCaseWithNullName() {
        val result = provider.upperCase("TeST", null)
        assertTrue(result.contains("'string'"))
        assertTrue(result.contains("lowercase characters"))
    }

    @Test
    fun upperCaseWithMultipleLowercase() {
        val result = provider.upperCase("HeLLo", "text")
        assertTrue(result.contains("'text'"))
        assertTrue(result.contains("[e, o]"))
    }
}
