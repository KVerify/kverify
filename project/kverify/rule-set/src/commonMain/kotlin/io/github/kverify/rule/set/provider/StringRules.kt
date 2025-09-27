package io.github.kverify.rule.set.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.string.StringAlphabeticRule
import io.github.kverify.rule.set.string.StringAlphanumericRule
import io.github.kverify.rule.set.string.StringContainsAllRule
import io.github.kverify.rule.set.string.StringContainsNoneRule
import io.github.kverify.rule.set.string.StringContainsOnlyRule
import io.github.kverify.rule.set.string.StringContainsRegexRule
import io.github.kverify.rule.set.string.StringContainsRule
import io.github.kverify.rule.set.string.StringEndsWithRule
import io.github.kverify.rule.set.string.StringLengthBetweenRule
import io.github.kverify.rule.set.string.StringLengthNotBetweenRule
import io.github.kverify.rule.set.string.StringLowerCaseRule
import io.github.kverify.rule.set.string.StringMatchesRule
import io.github.kverify.rule.set.string.StringMaxLengthRule
import io.github.kverify.rule.set.string.StringMinLengthRule
import io.github.kverify.rule.set.string.StringNotBlankRule
import io.github.kverify.rule.set.string.StringNotContainsRegexRule
import io.github.kverify.rule.set.string.StringNotContainsRule
import io.github.kverify.rule.set.string.StringNotEmptyRule
import io.github.kverify.rule.set.string.StringNotMatchesRule
import io.github.kverify.rule.set.string.StringNotOfLengthRule
import io.github.kverify.rule.set.string.StringNumericRule
import io.github.kverify.rule.set.string.StringOfLengthRule
import io.github.kverify.rule.set.string.StringStartsWithRule
import io.github.kverify.rule.set.string.StringUpperCaseRule
import io.github.kverify.violation.factory.provider.StringViolationFactories
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider
import io.github.kverify.violation.set.provider.StringViolations

public class StringRules(
    public val stringViolationFactoryProvider: StringViolationFactoryProvider =
        StringViolationFactoryProvider.Default,
) : StringRuleProvider,
    StringRuleWithFactoryProvider {
    public constructor(
        stringViolationProvider: StringViolationProvider,
    ) : this(
        stringViolationFactoryProvider =
            StringViolationFactories(
                stringViolationProvider = stringViolationProvider,
            ),
    )

    public constructor(
        stringViolationLocalizationProvider: StringViolationLocalizationProvider,
    ) : this(
        stringViolationProvider =
            StringViolations(
                stringViolationLocalizationProvider = stringViolationLocalizationProvider,
            ),
    )

    override fun alphabetic(): Rule<String> =
        StringAlphabeticRule(
            violationFactory = stringViolationFactoryProvider.alphabetic(),
        )

    override fun alphanumeric(): Rule<String> =
        StringAlphanumericRule(
            violationFactory = stringViolationFactoryProvider.alphanumeric(),
        )

    override fun containsAll(chars: Iterable<Char>): Rule<String> =
        StringContainsAllRule(
            chars = chars,
            violationFactory = stringViolationFactoryProvider.containsAll(chars),
        )

    override fun containsNone(chars: Iterable<Char>): Rule<String> =
        StringContainsNoneRule(
            chars = chars,
            violationFactory = stringViolationFactoryProvider.containsNone(chars),
        )

    override fun containsOnly(chars: Iterable<Char>): Rule<String> =
        StringContainsOnlyRule(
            chars = chars,
            violationFactory = stringViolationFactoryProvider.containsOnly(chars),
        )

    override fun containsRegex(regex: Regex): Rule<String> =
        StringContainsRegexRule(
            regex = regex,
            violationFactory = stringViolationFactoryProvider.containsRegex(regex),
        )

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
    ): Rule<String> =
        StringContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = stringViolationFactoryProvider.contains(substring, ignoreCase),
        )

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): Rule<String> =
        StringEndsWithRule(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationFactory = stringViolationFactoryProvider.endsWith(suffix, ignoreCase),
        )

    override fun lengthBetween(lengthRange: IntRange): Rule<String> =
        StringLengthBetweenRule(
            lengthRange = lengthRange,
            violationFactory = stringViolationFactoryProvider.lengthBetween(lengthRange),
        )

    override fun lengthNotBetween(lengthRange: IntRange): Rule<String> =
        StringLengthNotBetweenRule(
            lengthRange = lengthRange,
            violationFactory = stringViolationFactoryProvider.lengthNotBetween(lengthRange),
        )

    override fun lowerCase(): Rule<String> =
        StringLowerCaseRule(
            violationFactory = stringViolationFactoryProvider.lowerCase(),
        )

    override fun matches(regex: Regex): Rule<String> =
        StringMatchesRule(
            regex = regex,
            violationFactory = stringViolationFactoryProvider.matches(regex),
        )

    override fun maxLength(maxLength: Int): Rule<String> =
        StringMaxLengthRule(
            maxLength = maxLength,
            violationFactory = stringViolationFactoryProvider.maxLength(maxLength),
        )

    override fun minLength(minLength: Int): Rule<String> =
        StringMinLengthRule(
            minLength = minLength,
            violationFactory = stringViolationFactoryProvider.minLength(minLength),
        )

    override fun notBlank(): Rule<String> =
        StringNotBlankRule(
            violationFactory = stringViolationFactoryProvider.notBlank(),
        )

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
    ): Rule<String> =
        StringNotContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = stringViolationFactoryProvider.notContains(substring, ignoreCase),
        )

    override fun notContainsRegex(regex: Regex): Rule<String> =
        StringNotContainsRegexRule(
            regex = regex,
            violationFactory = stringViolationFactoryProvider.notContainsRegex(regex),
        )

    override fun notEmpty(): Rule<String> =
        StringNotEmptyRule(
            violationFactory = stringViolationFactoryProvider.notEmpty(),
        )

    override fun notMatches(regex: Regex): Rule<String> =
        StringNotMatchesRule(
            regex = regex,
            violationFactory = stringViolationFactoryProvider.notMatches(regex),
        )

    override fun notOfLength(length: Int): Rule<String> =
        StringNotOfLengthRule(
            length = length,
            violationFactory = stringViolationFactoryProvider.notOfLength(length),
        )

    override fun numeric(): Rule<String> =
        StringNumericRule(
            violationFactory = stringViolationFactoryProvider.numeric(),
        )

    override fun ofLength(length: Int): Rule<String> =
        StringOfLengthRule(
            length = length,
            violationFactory = stringViolationFactoryProvider.ofLength(length),
        )

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): Rule<String> =
        StringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationFactory = stringViolationFactoryProvider.startsWith(prefix, ignoreCase),
        )

    override fun upperCase(): Rule<String> =
        StringUpperCaseRule(
            violationFactory = stringViolationFactoryProvider.upperCase(),
        )

    override fun alphabetic(violationFactory: ViolationFactory<String>): Rule<String> =
        StringAlphabeticRule(
            violationFactory = violationFactory,
        )

    override fun alphanumeric(violationFactory: ViolationFactory<String>): Rule<String> =
        StringAlphanumericRule(
            violationFactory = violationFactory,
        )

    override fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringContainsAllRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringContainsNoneRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringContainsOnlyRule(
            chars = chars,
            violationFactory = violationFactory,
        )

    override fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringContainsRegexRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringEndsWithRule(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringLengthBetweenRule(
            lengthRange = lengthRange,
            violationFactory = violationFactory,
        )

    override fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringLengthNotBetweenRule(
            lengthRange = lengthRange,
            violationFactory = violationFactory,
        )

    override fun lowerCase(violationFactory: ViolationFactory<String>): Rule<String> =
        StringLowerCaseRule(
            violationFactory = violationFactory,
        )

    override fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringMatchesRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringMaxLengthRule(
            maxLength = maxLength,
            violationFactory = violationFactory,
        )

    override fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringMinLengthRule(
            minLength = minLength,
            violationFactory = violationFactory,
        )

    override fun notBlank(violationFactory: ViolationFactory<String>): Rule<String> =
        StringNotBlankRule(
            violationFactory = violationFactory,
        )

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringNotContainsRule(
            substring = substring,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringNotContainsRegexRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun notEmpty(violationFactory: ViolationFactory<String>): Rule<String> =
        StringNotEmptyRule(
            violationFactory = violationFactory,
        )

    override fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringNotMatchesRule(
            regex = regex,
            violationFactory = violationFactory,
        )

    override fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringNotOfLengthRule(
            length = length,
            violationFactory = violationFactory,
        )

    override fun numeric(violationFactory: ViolationFactory<String>): Rule<String> =
        StringNumericRule(
            violationFactory = violationFactory,
        )

    override fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringOfLengthRule(
            length = length,
            violationFactory = violationFactory,
        )

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): Rule<String> =
        StringStartsWithRule(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationFactory = violationFactory,
        )

    override fun upperCase(violationFactory: ViolationFactory<String>): Rule<String> =
        StringUpperCaseRule(
            violationFactory = violationFactory,
        )
}
