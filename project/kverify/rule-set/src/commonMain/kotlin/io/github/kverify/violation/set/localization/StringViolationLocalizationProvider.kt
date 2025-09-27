package io.github.kverify.violation.set.localization

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
