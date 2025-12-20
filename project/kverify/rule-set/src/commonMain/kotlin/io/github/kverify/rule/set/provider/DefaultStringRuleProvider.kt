package io.github.kverify.rule.set.provider

import io.github.kverify.check.set.string.StringAlphabeticCheck
import io.github.kverify.check.set.string.StringAlphanumericCheck
import io.github.kverify.check.set.string.StringContainsAllCheck
import io.github.kverify.check.set.string.StringContainsCheck
import io.github.kverify.check.set.string.StringContainsNoneCheck
import io.github.kverify.check.set.string.StringContainsOnlyCheck
import io.github.kverify.check.set.string.StringContainsRegexCheck
import io.github.kverify.check.set.string.StringEndsWithCheck
import io.github.kverify.check.set.string.StringLengthBetweenCheck
import io.github.kverify.check.set.string.StringLengthNotBetweenCheck
import io.github.kverify.check.set.string.StringLowerCaseCheck
import io.github.kverify.check.set.string.StringMatchesCheck
import io.github.kverify.check.set.string.StringMaxLengthCheck
import io.github.kverify.check.set.string.StringMinLengthCheck
import io.github.kverify.check.set.string.StringNotBlankCheck
import io.github.kverify.check.set.string.StringNotContainsCheck
import io.github.kverify.check.set.string.StringNotContainsRegexCheck
import io.github.kverify.check.set.string.StringNotEmptyCheck
import io.github.kverify.check.set.string.StringNotMatchesCheck
import io.github.kverify.check.set.string.StringNotOfLengthCheck
import io.github.kverify.check.set.string.StringNumericCheck
import io.github.kverify.check.set.string.StringOfLengthCheck
import io.github.kverify.check.set.string.StringStartsWithCheck
import io.github.kverify.check.set.string.StringUpperCaseCheck
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.DefaultStringViolationFactoryProvider
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.DefaultStringViolationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class DefaultStringRuleProvider(
    override val stringViolationFactoryProvider: StringViolationFactoryProvider =
        StringViolationFactoryProvider.Default,
) : StringRuleProvider {
    public constructor(
        stringViolationProvider: StringViolationProvider,
    ) : this(
        stringViolationFactoryProvider =
            DefaultStringViolationFactoryProvider(
                stringViolationProvider = stringViolationProvider,
            ),
    )

    public constructor(
        stringViolationLocalizationProvider: StringViolationLocalizationProvider,
    ) : this(
        stringViolationProvider =
            DefaultStringViolationProvider(
                stringViolationLocalizationProvider = stringViolationLocalizationProvider,
            ),
    )

    override fun alphabetic(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringAlphabeticCheck,
            violationFactory = violationFactory,
        )

    override fun alphanumeric(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringAlphanumericCheck,
            violationFactory = violationFactory,
        )

    override fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringContainsAllCheck(chars),
            violationFactory = violationFactory,
        )

    override fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringContainsNoneCheck(chars),
            violationFactory = violationFactory,
        )

    override fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringContainsOnlyCheck(chars),
            violationFactory = violationFactory,
        )

    override fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringContainsRegexCheck(regex),
            violationFactory = violationFactory,
        )

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringContainsCheck(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringEndsWithCheck(suffix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringLengthBetweenCheck(lengthRange),
            violationFactory = violationFactory,
        )

    override fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringLengthNotBetweenCheck(lengthRange),
            violationFactory = violationFactory,
        )

    override fun lowerCase(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringLowerCaseCheck,
            violationFactory = violationFactory,
        )

    override fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringMatchesCheck(regex),
            violationFactory = violationFactory,
        )

    override fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringMaxLengthCheck(maxLength),
            violationFactory = violationFactory,
        )

    override fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringMinLengthCheck(minLength),
            violationFactory = violationFactory,
        )

    override fun notBlank(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotBlankCheck,
            violationFactory = violationFactory,
        )

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotContainsCheck(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotContainsRegexCheck(regex),
            violationFactory = violationFactory,
        )

    override fun notEmpty(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotEmptyCheck,
            violationFactory = violationFactory,
        )

    override fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotMatchesCheck(regex),
            violationFactory = violationFactory,
        )

    override fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNotOfLengthCheck(length),
            violationFactory = violationFactory,
        )

    override fun numeric(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringNumericCheck,
            violationFactory = violationFactory,
        )

    override fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringOfLengthCheck(length),
            violationFactory = violationFactory,
        )

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringStartsWithCheck(prefix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun upperCase(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = StringUpperCaseCheck,
            violationFactory = violationFactory,
        )
}
