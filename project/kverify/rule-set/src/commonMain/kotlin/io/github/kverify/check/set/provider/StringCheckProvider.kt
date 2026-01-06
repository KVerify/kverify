package io.github.kverify.check.set.provider

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public interface StringCheckProvider {
    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
    ): ValidationCheck<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): ValidationCheck<String>

    public fun containsAll(chars: Iterable<Char>): ValidationCheck<String>

    public fun containsOnly(chars: Iterable<Char>): ValidationCheck<String>

    public fun containsNone(chars: Iterable<Char>): ValidationCheck<String>

    public fun notEmpty(): ValidationCheck<String>

    public fun notBlank(): ValidationCheck<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): ValidationCheck<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): ValidationCheck<String>

    public fun matches(regex: Regex): ValidationCheck<String>

    public fun notMatches(regex: Regex): ValidationCheck<String>

    public fun containsRegex(regex: Regex): ValidationCheck<String>

    public fun notContainsRegex(regex: Regex): ValidationCheck<String>

    public fun alphabetic(): ValidationCheck<String>

    public fun alphanumeric(): ValidationCheck<String>

    public fun numeric(): ValidationCheck<String>

    public fun upperCase(): ValidationCheck<String>

    public fun lowerCase(): ValidationCheck<String>

    public fun ofLength(length: Int): ValidationCheck<String>

    public fun notOfLength(length: Int): ValidationCheck<String>

    public fun minLength(minLength: Int): ValidationCheck<String>

    public fun maxLength(maxLength: Int): ValidationCheck<String>

    public fun lengthBetween(lengthRange: IntRange): ValidationCheck<String>

    public fun lengthNotBetween(lengthRange: IntRange): ValidationCheck<String>

    public companion object {
        public val Default: StringCheckProvider = DefaultStringCheckProvider()
    }
}
