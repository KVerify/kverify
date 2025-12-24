package io.github.kverify.check.set.provider

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DefaultStringCheckProviderTest {
    private val provider = DefaultStringCheckProvider()

    @Test
    fun containsReturnsTrueWhenSubstringPresent() {
        val check = provider.contains("test")
        assertTrue(check.isValid("this is a test"))
    }

    @Test
    fun containsReturnsFalseWhenSubstringAbsent() {
        val check = provider.contains("missing")
        assertFalse(check.isValid("this is a test"))
    }

    @Test
    fun containsReturnsTrueWhenSubstringPresentIgnoreCase() {
        val check = provider.contains("TEST", ignoreCase = true)
        assertTrue(check.isValid("this is a test"))
    }

    @Test
    fun containsReturnsFalseWhenCaseDiffersAndNotIgnored() {
        val check = provider.contains("TEST", ignoreCase = false)
        assertFalse(check.isValid("this is a test"))
    }

    @Test
    fun notContainsReturnsTrueWhenSubstringAbsent() {
        val check = provider.notContains("missing")
        assertTrue(check.isValid("this is a test"))
    }

    @Test
    fun notContainsReturnsFalseWhenSubstringPresent() {
        val check = provider.notContains("test")
        assertFalse(check.isValid("this is a test"))
    }

    @Test
    fun notContainsReturnsFalseWhenSubstringPresentIgnoreCase() {
        val check = provider.notContains("TEST", ignoreCase = true)
        assertFalse(check.isValid("this is a test"))
    }

    @Test
    fun notContainsReturnsTrueWhenCaseDiffersAndNotIgnored() {
        val check = provider.notContains("TEST", ignoreCase = false)
        assertTrue(check.isValid("this is a test"))
    }

    @Test
    fun containsAllReturnsTrueWhenAllCharsPresent() {
        val check = provider.containsAll(listOf('a', 'b', 'c'))
        assertTrue(check.isValid("abc"))
    }

    @Test
    fun containsAllReturnsTrueWhenAllCharsPresentInDifferentOrder() {
        val check = provider.containsAll(listOf('c', 'a', 'b'))
        assertTrue(check.isValid("abcdef"))
    }

    @Test
    fun containsAllReturnsFalseWhenSomeCharsMissing() {
        val check = provider.containsAll(listOf('x', 'y', 'z'))
        assertFalse(check.isValid("abc"))
    }

    @Test
    fun containsOnlyReturnsTrueWhenOnlyAllowedCharsPresent() {
        val check = provider.containsOnly(listOf('a', 'b', 'c'))
        assertTrue(check.isValid("abccba"))
    }

    @Test
    fun containsOnlyReturnsFalseWhenDisallowedCharPresent() {
        val check = provider.containsOnly(listOf('a', 'b'))
        assertFalse(check.isValid("abc"))
    }

    @Test
    fun containsOnlyReturnsTrueForEmptyString() {
        val check = provider.containsOnly(listOf('a'))
        assertTrue(check.isValid(""))
    }

    @Test
    fun containsNoneReturnsTrueWhenNoForbiddenCharsPresent() {
        val check = provider.containsNone(listOf('x', 'y', 'z'))
        assertTrue(check.isValid("abc"))
    }

    @Test
    fun containsNoneReturnsFalseWhenAnyForbiddenCharPresent() {
        val check = provider.containsNone(listOf('b', 'x'))
        assertFalse(check.isValid("abc"))
    }

    @Test
    fun containsNoneReturnsTrueForEmptyString() {
        val check = provider.containsNone(listOf('a'))
        assertTrue(check.isValid(""))
    }

    @Test
    fun notEmptyReturnsTrueForNonEmptyString() {
        val check = provider.notEmpty()
        assertTrue(check.isValid("test"))
    }

    @Test
    fun notEmptyReturnsFalseForEmptyString() {
        val check = provider.notEmpty()
        assertFalse(check.isValid(""))
    }

    @Test
    fun notBlankReturnsTrueForNonBlankString() {
        val check = provider.notBlank()
        assertTrue(check.isValid("test"))
    }

    @Test
    fun notBlankReturnsFalseForBlankString() {
        val check = provider.notBlank()
        assertFalse(check.isValid("   "))
    }

    @Test
    fun notBlankReturnsFalseForEmptyString() {
        val check = provider.notBlank()
        assertFalse(check.isValid(""))
    }

    @Test
    fun startsWithReturnsTrueWhenPrefixMatches() {
        val check = provider.startsWith("test")
        assertTrue(check.isValid("testing"))
    }

    @Test
    fun startsWithReturnsFalseWhenPrefixDoesNotMatch() {
        val check = provider.startsWith("test")
        assertFalse(check.isValid("something"))
    }

    @Test
    fun startsWithReturnsTrueWhenPrefixMatchesIgnoreCase() {
        val check = provider.startsWith("TEST", ignoreCase = true)
        assertTrue(check.isValid("testing"))
    }

    @Test
    fun startsWithReturnsFalseWhenCaseDiffersAndNotIgnored() {
        val check = provider.startsWith("TEST", ignoreCase = false)
        assertFalse(check.isValid("testing"))
    }

    @Test
    fun endsWithReturnsTrueWhenSuffixMatches() {
        val check = provider.endsWith("ing")
        assertTrue(check.isValid("testing"))
    }

    @Test
    fun endsWithReturnsFalseWhenSuffixDoesNotMatch() {
        val check = provider.endsWith("ing")
        assertFalse(check.isValid("tested"))
    }

    @Test
    fun endsWithReturnsTrueWhenSuffixMatchesIgnoreCase() {
        val check = provider.endsWith("ING", ignoreCase = true)
        assertTrue(check.isValid("testing"))
    }

    @Test
    fun endsWithReturnsFalseWhenCaseDiffersAndNotIgnored() {
        val check = provider.endsWith("ING", ignoreCase = false)
        assertFalse(check.isValid("testing"))
    }

    @Test
    fun matchesReturnsTrueWhenRegexMatches() {
        val check = provider.matches(Regex("^[a-z]+$"))
        assertTrue(check.isValid("test"))
    }

    @Test
    fun matchesReturnsFalseWhenRegexDoesNotMatch() {
        val check = provider.matches(Regex("^[a-z]+$"))
        assertFalse(check.isValid("test123"))
    }

    @Test
    fun notMatchesReturnsTrueWhenRegexDoesNotMatch() {
        val check = provider.notMatches(Regex("^[a-z]+$"))
        assertTrue(check.isValid("test123"))
    }

    @Test
    fun notMatchesReturnsFalseWhenRegexMatches() {
        val check = provider.notMatches(Regex("^[a-z]+$"))
        assertFalse(check.isValid("test"))
    }

    @Test
    fun containsRegexReturnsTrueWhenPatternFound() {
        val check = provider.containsRegex(Regex("[0-9]+"))
        assertTrue(check.isValid("test123"))
    }

    @Test
    fun containsRegexReturnsFalseWhenPatternNotFound() {
        val check = provider.containsRegex(Regex("[0-9]+"))
        assertFalse(check.isValid("test"))
    }

    @Test
    fun notContainsRegexReturnsTrueWhenPatternNotFound() {
        val check = provider.notContainsRegex(Regex("[0-9]+"))
        assertTrue(check.isValid("test"))
    }

    @Test
    fun notContainsRegexReturnsFalseWhenPatternFound() {
        val check = provider.notContainsRegex(Regex("[0-9]+"))
        assertFalse(check.isValid("test123"))
    }

    @Test
    fun alphabeticReturnsTrueForAllLetters() {
        val check = provider.alphabetic()
        assertTrue(check.isValid("test"))
    }

    @Test
    fun alphabeticReturnsFalseForDigits() {
        val check = provider.alphabetic()
        assertFalse(check.isValid("test123"))
    }

    @Test
    fun alphabeticReturnsFalseForSpecialChars() {
        val check = provider.alphabetic()
        assertFalse(check.isValid("test!"))
    }

    @Test
    fun alphabeticReturnsTrueForEmptyString() {
        val check = provider.alphabetic()
        assertTrue(check.isValid(""))
    }

    @Test
    fun alphanumericReturnsTrueForLettersAndDigits() {
        val check = provider.alphanumeric()
        assertTrue(check.isValid("test123"))
    }

    @Test
    fun alphanumericReturnsTrueForOnlyLetters() {
        val check = provider.alphanumeric()
        assertTrue(check.isValid("test"))
    }

    @Test
    fun alphanumericReturnsTrueForOnlyDigits() {
        val check = provider.alphanumeric()
        assertTrue(check.isValid("123"))
    }

    @Test
    fun alphanumericReturnsFalseForSpecialChars() {
        val check = provider.alphanumeric()
        assertFalse(check.isValid("test!"))
    }

    @Test
    fun alphanumericReturnsTrueForEmptyString() {
        val check = provider.alphanumeric()
        assertTrue(check.isValid(""))
    }

    @Test
    fun numericReturnsTrueForOnlyDigits() {
        val check = provider.numeric()
        assertTrue(check.isValid("123"))
    }

    @Test
    fun numericReturnsFalseForLetters() {
        val check = provider.numeric()
        assertFalse(check.isValid("test"))
    }

    @Test
    fun numericReturnsFalseForMixedContent() {
        val check = provider.numeric()
        assertFalse(check.isValid("test123"))
    }

    @Test
    fun numericReturnsTrueForEmptyString() {
        val check = provider.numeric()
        assertTrue(check.isValid(""))
    }

    @Test
    fun upperCaseReturnsTrueForAllUpperCase() {
        val check = provider.upperCase()
        assertTrue(check.isValid("TEST"))
    }

    @Test
    fun upperCaseReturnsFalseForLowerCase() {
        val check = provider.upperCase()
        assertFalse(check.isValid("test"))
    }

    @Test
    fun upperCaseReturnsFalseForMixedCase() {
        val check = provider.upperCase()
        assertFalse(check.isValid("Test"))
    }

    @Test
    fun upperCaseReturnsTrueForEmptyString() {
        val check = provider.upperCase()
        assertTrue(check.isValid(""))
    }

    @Test
    fun lowerCaseReturnsTrueForAllLowerCase() {
        val check = provider.lowerCase()
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lowerCaseReturnsFalseForUpperCase() {
        val check = provider.lowerCase()
        assertFalse(check.isValid("TEST"))
    }

    @Test
    fun lowerCaseReturnsFalseForMixedCase() {
        val check = provider.lowerCase()
        assertFalse(check.isValid("Test"))
    }

    @Test
    fun lowerCaseReturnsTrueForEmptyString() {
        val check = provider.lowerCase()
        assertTrue(check.isValid(""))
    }

    @Test
    fun ofLengthReturnsTrueWhenLengthMatches() {
        val check = provider.ofLength(4)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun ofLengthReturnsFalseWhenLengthDiffers() {
        val check = provider.ofLength(3)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun ofLengthReturnsTrueForEmptyStringWithZeroLength() {
        val check = provider.ofLength(0)
        assertTrue(check.isValid(""))
    }

    @Test
    fun notOfLengthReturnsTrueWhenLengthDiffers() {
        val check = provider.notOfLength(3)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun notOfLengthReturnsFalseWhenLengthMatches() {
        val check = provider.notOfLength(4)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun minLengthReturnsTrueWhenLengthEqualsMinimum() {
        val check = provider.minLength(4)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun minLengthReturnsTrueWhenLengthGreaterThanMinimum() {
        val check = provider.minLength(3)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun minLengthReturnsFalseWhenLengthBelowMinimum() {
        val check = provider.minLength(5)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun maxLengthReturnsTrueWhenLengthEqualsMaximum() {
        val check = provider.maxLength(4)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun maxLengthReturnsTrueWhenLengthBelowMaximum() {
        val check = provider.maxLength(5)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun maxLengthReturnsFalseWhenLengthAboveMaximum() {
        val check = provider.maxLength(3)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun lengthBetweenReturnsTrueWhenLengthInRange() {
        val check = provider.lengthBetween(3..5)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lengthBetweenReturnsTrueWhenLengthAtLowerBound() {
        val check = provider.lengthBetween(4..6)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lengthBetweenReturnsTrueWhenLengthAtUpperBound() {
        val check = provider.lengthBetween(2..4)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lengthBetweenReturnsFalseWhenLengthBelowRange() {
        val check = provider.lengthBetween(5..7)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun lengthBetweenReturnsFalseWhenLengthAboveRange() {
        val check = provider.lengthBetween(1..3)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun lengthNotBetweenReturnsTrueWhenLengthBelowRange() {
        val check = provider.lengthNotBetween(5..7)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lengthNotBetweenReturnsTrueWhenLengthAboveRange() {
        val check = provider.lengthNotBetween(1..3)
        assertTrue(check.isValid("test"))
    }

    @Test
    fun lengthNotBetweenReturnsFalseWhenLengthInRange() {
        val check = provider.lengthNotBetween(3..5)
        assertFalse(check.isValid("test"))
    }

    @Test
    fun lengthNotBetweenReturnsFalseWhenLengthAtBounds() {
        val check = provider.lengthNotBetween(4..4)
        assertFalse(check.isValid("test"))
    }
}
