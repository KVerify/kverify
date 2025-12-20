package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

@Suppress("TooManyFunctions")
public interface NamedStringRuleProvider {
    public val namedStringViolationFactoryProvider: NamedStringViolationFactoryProvider
        get() = NamedStringViolationFactoryProvider.Default

    public fun namedAlphabetic(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedAlphabetic(),
    ): NamedRule<String>

    public fun namedAlphanumeric(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedAlphanumeric(),
    ): NamedRule<String>

    public fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedContainsAll(chars),
    ): NamedRule<String>

    public fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedContainsNone(chars),
    ): NamedRule<String>

    public fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedContainsOnly(chars),
    ): NamedRule<String>

    public fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedContainsRegex(regex),
    ): NamedRule<String>

    public fun namedContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedContains(
                substring,
                ignoreCase,
            ),
    ): NamedRule<String>

    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedEndsWith(
                suffix,
                ignoreCase,
            ),
    ): NamedRule<String>

    public fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedLengthBetween(
                lengthRange,
            ),
    ): NamedRule<String>

    public fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedLengthNotBetween(
                lengthRange,
            ),
    ): NamedRule<String>

    public fun namedLowerCase(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedLowerCase(),
    ): NamedRule<String>

    public fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedMatches(regex),
    ): NamedRule<String>

    public fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedMaxLength(maxLength),
    ): NamedRule<String>

    public fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedMinLength(minLength),
    ): NamedRule<String>

    public fun namedNotBlank(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedNotBlank(),
    ): NamedRule<String>

    public fun namedNotContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedNotContains(
                substring,
                ignoreCase,
            ),
    ): NamedRule<String>

    public fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedNotContainsRegex(
                regex,
            ),
    ): NamedRule<String>

    public fun namedNotEmpty(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedNotEmpty(),
    ): NamedRule<String>

    public fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedNotMatches(regex),
    ): NamedRule<String>

    public fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedNotOfLength(length),
    ): NamedRule<String>

    public fun namedNumeric(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedNumeric(),
    ): NamedRule<String>

    public fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedOfLength(length),
    ): NamedRule<String>

    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            namedStringViolationFactoryProvider.namedStartsWith(
                prefix,
                ignoreCase,
            ),
    ): NamedRule<String>

    public fun namedUpperCase(
        violationFactory: NamedViolationFactory<String> = namedStringViolationFactoryProvider.namedUpperCase(),
    ): NamedRule<String>
}
