package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule

@Suppress("TooManyFunctions")
public interface NamedStringRuleProvider {
    public fun namedAlphabetic(): NamedRule<String>

    public fun namedAlphanumeric(): NamedRule<String>

    public fun namedContainsAll(chars: Iterable<Char>): NamedRule<String>

    public fun namedContainsNone(chars: Iterable<Char>): NamedRule<String>

    public fun namedContainsOnly(chars: Iterable<Char>): NamedRule<String>

    public fun namedContainsRegex(regex: Regex): NamedRule<String>

    public fun namedContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): NamedRule<String>

    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): NamedRule<String>

    public fun namedLengthBetween(lengthRange: IntRange): NamedRule<String>

    public fun namedLengthNotBetween(lengthRange: IntRange): NamedRule<String>

    public fun namedLowerCase(): NamedRule<String>

    public fun namedMatches(regex: Regex): NamedRule<String>

    public fun namedMaxLength(maxLength: Int): NamedRule<String>

    public fun namedMinLength(minLength: Int): NamedRule<String>

    public fun namedNotBlank(): NamedRule<String>

    public fun namedNotContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): NamedRule<String>

    public fun namedNotContainsRegex(regex: Regex): NamedRule<String>

    public fun namedNotEmpty(): NamedRule<String>

    public fun namedNotMatches(regex: Regex): NamedRule<String>

    public fun namedNotOfLength(length: Int): NamedRule<String>

    public fun namedNumeric(): NamedRule<String>

    public fun namedOfLength(length: Int): NamedRule<String>

    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): NamedRule<String>

    public fun namedUpperCase(): NamedRule<String>
}

@Suppress("TooManyFunctions")
public interface NamedStringRuleWithFactoryProvider {
    public fun namedAlphabetic(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedAlphanumeric(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedLowerCase(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedNotBlank(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedNotContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedNotEmpty(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedNumeric(violationFactory: NamedViolationFactory<String>): NamedRule<String>

    public fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String>

    public fun namedUpperCase(violationFactory: NamedViolationFactory<String>): NamedRule<String>
}
