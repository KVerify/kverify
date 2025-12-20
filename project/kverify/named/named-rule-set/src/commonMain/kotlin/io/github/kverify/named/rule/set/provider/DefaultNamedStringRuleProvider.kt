package io.github.kverify.named.rule.set.provider

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
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.named.violation.factory.provider.DefaultNamedStringViolationFactoryProvider
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.DefaultStringViolationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedStringRuleProvider(
    public override val namedStringViolationFactoryProvider: NamedStringViolationFactoryProvider =
        NamedStringViolationFactoryProvider.Default,
) : NamedStringRuleProvider {
    public constructor(
        stringViolationProvider: StringViolationProvider,
    ) : this(
        namedStringViolationFactoryProvider =
            DefaultNamedStringViolationFactoryProvider(
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

    override fun namedAlphabetic(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringAlphabeticCheck,
            violationFactory = violationFactory,
        )

    override fun namedAlphanumeric(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringAlphanumericCheck,
            violationFactory = violationFactory,
        )

    override fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringContainsAllCheck(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringContainsNoneCheck(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringContainsOnlyCheck(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringContainsRegexCheck(regex),
            violationFactory = violationFactory,
        )

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringContainsCheck(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringEndsWithCheck(suffix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringLengthBetweenCheck(lengthRange),
            violationFactory = violationFactory,
        )

    override fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringLengthNotBetweenCheck(lengthRange),
            violationFactory = violationFactory,
        )

    override fun namedLowerCase(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringLowerCaseCheck,
            violationFactory = violationFactory,
        )

    override fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringMatchesCheck(regex),
            violationFactory = violationFactory,
        )

    override fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringMaxLengthCheck(maxLength),
            violationFactory = violationFactory,
        )

    override fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringMinLengthCheck(minLength),
            violationFactory = violationFactory,
        )

    override fun namedNotBlank(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotBlankCheck,
            violationFactory = violationFactory,
        )

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotContainsCheck(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotContainsRegexCheck(regex),
            violationFactory = violationFactory,
        )

    override fun namedNotEmpty(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotEmptyCheck,
            violationFactory = violationFactory,
        )

    override fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotMatchesCheck(regex),
            violationFactory = violationFactory,
        )

    override fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNotOfLengthCheck(length),
            violationFactory = violationFactory,
        )

    override fun namedNumeric(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringNumericCheck,
            violationFactory = violationFactory,
        )

    override fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringOfLengthCheck(length),
            violationFactory = violationFactory,
        )

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringStartsWithCheck(prefix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedUpperCase(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = StringUpperCaseCheck,
            violationFactory = violationFactory,
        )
}
