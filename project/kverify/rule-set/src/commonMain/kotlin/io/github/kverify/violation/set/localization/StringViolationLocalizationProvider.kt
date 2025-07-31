package io.github.kverify.violation.set.localization

import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveComparableName
import io.github.kverify.violation.set.resolveStringName

@Suppress("TooManyFunctions")
public interface StringViolationLocalizationProvider {
    public fun alphabetic(
        value: String,
        name: String? = null,
    ): String

    public fun alphanumeric(
        value: String,
        name: String? = null,
    ): String

    public fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): String

    public fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): String

    public fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): String

    public fun containsRegex(
        value: String,
        regex: Regex,
        name: String? = null,
    ): String

    public fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): String

    public fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): String

    public fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String? = null,
    ): String

    public fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String? = null,
    ): String

    public fun lowerCase(
        value: String,
        name: String? = null,
    ): String

    public fun matches(
        value: String,
        regex: Regex,
        name: String? = null,
    ): String

    public fun maxLength(
        value: String,
        maxLength: Int,
        name: String? = null,
    ): String

    public fun minLength(
        value: String,
        minLength: Int,
        name: String? = null,
    ): String

    public fun notBlank(
        value: String,
        name: String? = null,
    ): String

    public fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): String

    public fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String? = null,
    ): String

    public fun notEmpty(
        value: String,
        name: String? = null,
    ): String

    public fun notMatches(
        value: String,
        regex: Regex,
        name: String? = null,
    ): String

    public fun notOfLength(
        value: String,
        length: Int,
        name: String? = null,
    ): String

    public fun numeric(
        value: String,
        name: String? = null,
    ): String

    public fun ofLength(
        value: String,
        length: Int,
        name: String? = null,
    ): String

    public fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): String

    public fun upperCase(
        value: String,
        name: String? = null,
    ): String

    public companion object {
        public val Default: StringViolationLocalizationProvider = DefaultStringViolationLocalizationProvider
    }
}

internal object DefaultStringViolationLocalizationProvider : StringViolationLocalizationProvider {
    override fun alphabetic(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars = value.asIterable().filterNot { it.isLetter() }.joinWithLimitAndBrackets()

        return "$displayName must contain only letters, but it also contains: $unexpectedChars."
    }

    override fun alphanumeric(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars = value.asIterable().filterNot { it.isLetterOrDigit() }.joinWithLimitAndBrackets()

        return "$displayName must contain only numbers and letters, but it also contains: $unexpectedChars."
    }

    override fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val charsAsString = chars.joinWithLimitAndBrackets()
        val missingChars = value.asIterable().filterNot { it in chars }.joinWithLimitAndBrackets()

        return "$displayName must contain all of the following characters: $charsAsString, but these are missing: $missingChars."
    }

    override fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name ?: "comparable")
        val charsAsString = chars.joinWithLimitAndBrackets()
        val forbiddenChars = value.asIterable().filter { it in chars }.joinWithLimitAndBrackets()

        return "$displayName must not contain any of the following characters: $charsAsString, but these are present: $forbiddenChars."
    }

    override fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val allowedChars = chars.joinWithLimitAndBrackets()
        val unexpectedChars = value.asIterable().filterNot { it in chars }.joinWithLimitAndBrackets()

        return "$displayName must contain only the following characters: $allowedChars, but it also contains: $unexpectedChars."
    }

    override fun containsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveComparableName(name)

        return "$displayName must contain the following regex pattern: '${regex.pattern}', but it does not."
    }

    override fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must contain the following substring (ignoreCase=$ignoreCase): '$substring', but it does not."
    }

    override fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must end with the following suffix (ignoreCase=$ignoreCase): $suffix, but it does not."
    }

    override fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must have a length between ${lengthRange.start} and ${lengthRange.endInclusive} (inclusive), but its length is ${value.length}."
    }

    override fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT have a length between ${lengthRange.start} and ${lengthRange.endInclusive} (inclusive), but its length is ${value.length}."
    }

    override fun lowerCase(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val upperCaseChars = value.asIterable().filterNot { it.isLowerCase() }.joinWithLimitAndBrackets()

        return "$displayName must be entirely lower case, but it also contains upper case characters: $upperCaseChars."
    }

    override fun matches(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must match the regular expression: '${regex.pattern}'."
    }

    override fun maxLength(
        value: String,
        maxLength: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be $maxLength at most, but it is ${value.length}."
    }

    override fun minLength(
        value: String,
        minLength: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be at least $minLength, but it is ${value.length}."
    }

    override fun notBlank(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must not be blank."
    }

    override fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT contain the following substring (ignoreCase=$ignoreCase): '$substring', but it does."
    }

    override fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT contain the following regex pattern: '${regex.pattern}', but it does."
    }

    override fun notEmpty(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must not be empty."
    }

    override fun notMatches(
        value: String,
        regex: Regex,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must not match the regex pattern: '${regex.pattern}'."
    }

    override fun notOfLength(
        value: String,
        length: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must NOT be of length $length, but it is."
    }

    override fun numeric(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val unexpectedChars = value.asIterable().filterNot { it.isDigit() }.joinWithLimitAndBrackets()

        return "$displayName must contain only digits, but it also contains: $unexpectedChars"
    }

    override fun ofLength(
        value: String,
        length: Int,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName length must be exactly $length, but it is ${value.length}."
    }

    override fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)

        return "$displayName must start with the following prefix (ignoreCase=$ignoreCase): '$prefix', but it does not."
    }

    override fun upperCase(
        value: String,
        name: String?,
    ): String {
        val displayName = resolveStringName(name)
        val lowercaseChars = value.asIterable().filter { it.isLowerCase() }.joinWithLimitAndBrackets()

        return "$displayName must be entirely upper case, but it also contains lowercase characters: $lowercaseChars."
    }
}
