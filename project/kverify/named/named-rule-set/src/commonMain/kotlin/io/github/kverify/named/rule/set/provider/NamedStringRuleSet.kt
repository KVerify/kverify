package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.named.rule.set.string.NamedStringAlphabeticRule
import io.github.kverify.named.rule.set.string.NamedStringAlphanumericRule
import io.github.kverify.named.rule.set.string.NamedStringContainsAllRule
import io.github.kverify.named.rule.set.string.NamedStringContainsNoneRule
import io.github.kverify.named.rule.set.string.NamedStringContainsOnlyRule
import io.github.kverify.named.rule.set.string.NamedStringContainsRegexRule
import io.github.kverify.named.rule.set.string.NamedStringContainsRule
import io.github.kverify.named.rule.set.string.NamedStringEndsWithRule
import io.github.kverify.named.rule.set.string.NamedStringLengthBetweenRule
import io.github.kverify.named.rule.set.string.NamedStringLengthNotBetweenRule
import io.github.kverify.named.rule.set.string.NamedStringLowerCaseRule
import io.github.kverify.named.rule.set.string.NamedStringMatchesRule
import io.github.kverify.named.rule.set.string.NamedStringMaxLengthRule
import io.github.kverify.named.rule.set.string.NamedStringMinLengthRule
import io.github.kverify.named.rule.set.string.NamedStringNotBlankRule
import io.github.kverify.named.rule.set.string.NamedStringNotContainsRegexRule
import io.github.kverify.named.rule.set.string.NamedStringNotContainsRule
import io.github.kverify.named.rule.set.string.NamedStringNotEmptyRule
import io.github.kverify.named.rule.set.string.NamedStringNotMatchesRule
import io.github.kverify.named.rule.set.string.NamedStringNotOfLengthRule
import io.github.kverify.named.rule.set.string.NamedStringNumericRule
import io.github.kverify.named.rule.set.string.NamedStringOfLengthRule
import io.github.kverify.named.rule.set.string.NamedStringStartsWithRule
import io.github.kverify.named.rule.set.string.NamedStringUpperCaseRule
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider

@Suppress("TooManyFunctions")
public class NamedStringRuleSet(
    public val namedStringViolationFactoryProvider: NamedStringViolationFactoryProvider =
        NamedStringViolationFactoryProvider.Default,
) : NamedStringRuleProvider,
    NamedStringRuleWithFactoryProvider {
    override fun namedAlphabetic(): NamedRule<String> =
        NamedStringAlphabeticRule(
            violationFactory = namedStringViolationFactoryProvider.namedAlphabetic(),
        )

    override fun namedAlphanumeric(): NamedRule<String> =
        NamedStringAlphanumericRule(
            violationFactory = namedStringViolationFactoryProvider.namedAlphanumeric(),
        )

    override fun namedContainsAll(chars: Iterable<Char>): NamedRule<String> =
        NamedStringContainsAllRule(
            chars = chars,
            violationFactory =
                namedStringViolationFactoryProvider.namedContainsAll(
                    chars = chars,
                ),
        )

    override fun namedContainsNone(chars: Iterable<Char>): NamedRule<String> =
        NamedStringContainsNoneRule(
            chars = chars,
            violationFactory =
                namedStringViolationFactoryProvider.namedContainsNone(
                    chars = chars,
                ),
        )

    override fun namedContainsOnly(chars: Iterable<Char>): NamedRule<String> =
        NamedStringContainsOnlyRule(
            chars = chars,
            violationFactory =
                namedStringViolationFactoryProvider.namedContainsOnly(
                    chars = chars,
                ),
        )

    override fun namedContainsRegex(regex: Regex): NamedRule<String> =
        NamedStringContainsRegexRule(
            regex = regex,
            violationFactory =
                namedStringViolationFactoryProvider.namedContainsRegex(
                    regex = regex,
                ),
        )

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedRule<String> =
        NamedStringContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory =
                namedStringViolationFactoryProvider.namedContains(
                    substring = substring,
                    ignoreCase = ignoreCase,
                ),
        )

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): NamedRule<String> =
        NamedStringEndsWithRule(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationFactory =
                namedStringViolationFactoryProvider.namedEndsWith(
                    suffix = suffix,
                    ignoreCase = ignoreCase,
                ),
        )

    override fun namedLengthBetween(lengthRange: IntRange): NamedRule<String> =
        NamedStringLengthBetweenRule(
            lengthRange = lengthRange,
            violationFactory =
                namedStringViolationFactoryProvider.namedLengthBetween(
                    lengthRange = lengthRange,
                ),
        )

    override fun namedLengthNotBetween(lengthRange: IntRange): NamedRule<String> =
        NamedStringLengthNotBetweenRule(
            lengthRange = lengthRange,
            violationFactory =
                namedStringViolationFactoryProvider.namedLengthNotBetween(
                    lengthRange = lengthRange,
                ),
        )

    override fun namedLowerCase(): NamedRule<String> =
        NamedStringLowerCaseRule(
            violationFactory = namedStringViolationFactoryProvider.namedLowerCase(),
        )

    override fun namedMatches(regex: Regex): NamedRule<String> =
        NamedStringMatchesRule(
            regex = regex,
            violationFactory =
                namedStringViolationFactoryProvider.namedMatches(
                    regex = regex,
                ),
        )

    override fun namedMaxLength(maxLength: Int): NamedRule<String> =
        NamedStringMaxLengthRule(
            maxLength = maxLength,
            violationFactory =
                namedStringViolationFactoryProvider.namedMaxLength(
                    maxLength = maxLength,
                ),
        )

    override fun namedMinLength(minLength: Int): NamedRule<String> =
        NamedStringMinLengthRule(
            minLength = minLength,
            violationFactory =
                namedStringViolationFactoryProvider.namedMinLength(
                    minLength = minLength,
                ),
        )

    override fun namedNotBlank(): NamedRule<String> =
        NamedStringNotBlankRule(
            violationFactory = namedStringViolationFactoryProvider.namedNotBlank(),
        )

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedRule<String> =
        NamedStringNotContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory =
                namedStringViolationFactoryProvider.namedNotContains(
                    substring = substring,
                    ignoreCase = ignoreCase,
                ),
        )

    override fun namedNotContainsRegex(regex: Regex): NamedRule<String> =
        NamedStringNotContainsRegexRule(
            regex = regex,
            violationFactory =
                namedStringViolationFactoryProvider.namedNotContainsRegex(
                    regex = regex,
                ),
        )

    override fun namedNotEmpty(): NamedRule<String> =
        NamedStringNotEmptyRule(
            violationFactory = namedStringViolationFactoryProvider.namedNotEmpty(),
        )

    override fun namedNotMatches(regex: Regex): NamedRule<String> =
        NamedStringNotMatchesRule(
            regex = regex,
            violationFactory =
                namedStringViolationFactoryProvider.namedNotMatches(
                    regex = regex,
                ),
        )

    override fun namedNotOfLength(length: Int): NamedRule<String> =
        NamedStringNotOfLengthRule(
            length = length,
            violationFactory =
                namedStringViolationFactoryProvider.namedNotOfLength(
                    length = length,
                ),
        )

    override fun namedNumeric(): NamedRule<String> =
        NamedStringNumericRule(
            violationFactory = namedStringViolationFactoryProvider.namedNumeric(),
        )

    override fun namedOfLength(length: Int): NamedRule<String> =
        NamedStringOfLengthRule(
            length = length,
            violationFactory =
                namedStringViolationFactoryProvider.namedOfLength(
                    length = length,
                ),
        )

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): NamedRule<String> =
        NamedStringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationFactory =
                namedStringViolationFactoryProvider.namedStartsWith(
                    prefix = prefix,
                    ignoreCase = ignoreCase,
                ),
        )

    override fun namedUpperCase(): NamedRule<String> =
        NamedStringUpperCaseRule(
            violationFactory = namedStringViolationFactoryProvider.namedUpperCase(),
        )

    override fun namedAlphabetic(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringAlphabeticRule(
            violationFactory = violationFactory,
        )

    override fun namedAlphanumeric(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringAlphanumericRule(
            violationFactory = violationFactory,
        )

    override fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringContainsAllRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringContainsNoneRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringContainsOnlyRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringContainsRegexRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringEndsWithRule(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringLengthBetweenRule(
            lengthRange = lengthRange,
            violationFactory = violationFactory,
        )

    override fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringLengthNotBetweenRule(
            lengthRange = lengthRange,
            violationFactory = violationFactory,
        )

    override fun namedLowerCase(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringLowerCaseRule(
            violationFactory = violationFactory,
        )

    override fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringMatchesRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringMaxLengthRule(
            maxLength = maxLength,
            violationFactory = violationFactory,
        )

    override fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringMinLengthRule(
            minLength = minLength,
            violationFactory = violationFactory,
        )

    override fun namedNotBlank(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringNotBlankRule(
            violationFactory = violationFactory,
        )

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringNotContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringNotContainsRegexRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun namedNotEmpty(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringNotEmptyRule(
            violationFactory = violationFactory,
        )

    override fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringNotMatchesRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringNotOfLengthRule(
            length = length,
            violationFactory = violationFactory,
        )

    override fun namedNumeric(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringNumericRule(
            violationFactory = violationFactory,
        )

    override fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringOfLengthRule(
            length = length,
            violationFactory = violationFactory,
        )

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedRule<String> =
        NamedStringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun namedUpperCase(violationFactory: NamedViolationFactory<String>): NamedRule<String> =
        NamedStringUpperCaseRule(
            violationFactory = violationFactory,
        )
}
