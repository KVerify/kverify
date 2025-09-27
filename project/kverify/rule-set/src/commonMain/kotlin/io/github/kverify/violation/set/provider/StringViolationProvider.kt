package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation

@Suppress("TooManyFunctions")
public interface StringViolationProvider {
    public fun alphabetic(
        value: String,
        name: String? = null,
    ): Violation

    public fun alphanumeric(
        value: String,
        name: String? = null,
    ): Violation

    public fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): Violation

    public fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): Violation

    public fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String? = null,
    ): Violation

    public fun containsRegex(
        value: String,
        regex: Regex,
        name: String? = null,
    ): Violation

    public fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): Violation

    public fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): Violation

    public fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String? = null,
    ): Violation

    public fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String? = null,
    ): Violation

    public fun lowerCase(
        value: String,
        name: String? = null,
    ): Violation

    public fun matches(
        value: String,
        regex: Regex,
        name: String? = null,
    ): Violation

    public fun maxLength(
        value: String,
        maxLength: Int,
        name: String? = null,
    ): Violation

    public fun minLength(
        value: String,
        minLength: Int,
        name: String? = null,
    ): Violation

    public fun notBlank(
        value: String,
        name: String? = null,
    ): Violation

    public fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): Violation

    public fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String? = null,
    ): Violation

    public fun notEmpty(
        value: String,
        name: String? = null,
    ): Violation

    public fun notMatches(
        value: String,
        regex: Regex,
        name: String? = null,
    ): Violation

    public fun notOfLength(
        value: String,
        length: Int,
        name: String? = null,
    ): Violation

    public fun numeric(
        value: String,
        name: String? = null,
    ): Violation

    public fun ofLength(
        value: String,
        length: Int,
        name: String? = null,
    ): Violation

    public fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean = false,
        name: String? = null,
    ): Violation

    public fun upperCase(
        value: String,
        name: String? = null,
    ): Violation

    public companion object {
        public val Default: StringViolationProvider = StringViolations()
    }
}
