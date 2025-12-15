package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory

@Suppress("TooManyFunctions")
public interface NamedStringViolationFactoryProvider {
    public fun namedAlphabetic(): NamedViolationFactory<String>

    public fun namedAlphanumeric(): NamedViolationFactory<String>

    public fun namedContainsAll(chars: Iterable<Char>): NamedViolationFactory<String>

    public fun namedContainsNone(chars: Iterable<Char>): NamedViolationFactory<String>

    public fun namedContainsOnly(chars: Iterable<Char>): NamedViolationFactory<String>

    public fun namedContainsRegex(regex: Regex): NamedViolationFactory<String>

    public fun namedContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): NamedViolationFactory<String>

    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): NamedViolationFactory<String>

    public fun namedLengthBetween(lengthRange: IntRange): NamedViolationFactory<String>

    public fun namedLengthNotBetween(lengthRange: IntRange): NamedViolationFactory<String>

    public fun namedLowerCase(): NamedViolationFactory<String>

    public fun namedMatches(regex: Regex): NamedViolationFactory<String>

    public fun namedMaxLength(maxLength: Int): NamedViolationFactory<String>

    public fun namedMinLength(minLength: Int): NamedViolationFactory<String>

    public fun namedNotBlank(): NamedViolationFactory<String>

    public fun namedNotContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): NamedViolationFactory<String>

    public fun namedNotContainsRegex(regex: Regex): NamedViolationFactory<String>

    public fun namedNotEmpty(): NamedViolationFactory<String>

    public fun namedNotMatches(regex: Regex): NamedViolationFactory<String>

    public fun namedNotOfLength(length: Int): NamedViolationFactory<String>

    public fun namedNumeric(): NamedViolationFactory<String>

    public fun namedOfLength(length: Int): NamedViolationFactory<String>

    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): NamedViolationFactory<String>

    public fun namedUpperCase(): NamedViolationFactory<String>

    public companion object {
        public val Default: NamedStringViolationFactoryProvider = DefaultNamedStringViolationFactoryProvider()
    }
}
