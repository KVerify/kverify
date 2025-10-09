package io.github.kverify.violation.factory.provider

import io.github.kverify.core.check.ViolationFactory

@Suppress("TooManyFunctions")
public interface StringViolationFactoryProvider {
    public fun alphabetic(): ViolationFactory<String>

    public fun alphanumeric(): ViolationFactory<String>

    public fun containsAll(chars: Iterable<Char>): ViolationFactory<String>

    public fun containsNone(chars: Iterable<Char>): ViolationFactory<String>

    public fun containsOnly(chars: Iterable<Char>): ViolationFactory<String>

    public fun containsRegex(regex: Regex): ViolationFactory<String>

    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
    ): ViolationFactory<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): ViolationFactory<String>

    public fun lengthBetween(lengthRange: IntRange): ViolationFactory<String>

    public fun lengthNotBetween(lengthRange: IntRange): ViolationFactory<String>

    public fun lowerCase(): ViolationFactory<String>

    public fun matches(regex: Regex): ViolationFactory<String>

    public fun maxLength(maxLength: Int): ViolationFactory<String>

    public fun minLength(minLength: Int): ViolationFactory<String>

    public fun notBlank(): ViolationFactory<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): ViolationFactory<String>

    public fun notContainsRegex(regex: Regex): ViolationFactory<String>

    public fun notEmpty(): ViolationFactory<String>

    public fun notMatches(regex: Regex): ViolationFactory<String>

    public fun notOfLength(length: Int): ViolationFactory<String>

    public fun numeric(): ViolationFactory<String>

    public fun ofLength(length: Int): ViolationFactory<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): ViolationFactory<String>

    public fun upperCase(): ViolationFactory<String>

    public companion object {
        public val Default: StringViolationFactoryProvider = StringViolationFactorySet()
    }
}
