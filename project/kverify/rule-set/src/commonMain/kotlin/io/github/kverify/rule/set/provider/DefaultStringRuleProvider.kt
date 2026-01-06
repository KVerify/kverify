package io.github.kverify.rule.set.provider

import io.github.kverify.check.set.provider.StringCheckProvider
import io.github.kverify.core.rule.predicate.PredicateRule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.DefaultStringViolationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class DefaultStringRuleProvider(
    public val stringCheckProvider: StringCheckProvider = StringCheckProvider.Default,
    override val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : StringRuleProvider {
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
            validationCheck = stringCheckProvider.alphabetic(),
            violationFactory = violationFactory,
        )

    override fun alphanumeric(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.alphanumeric(),
            violationFactory = violationFactory,
        )

    override fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.containsAll(chars),
            violationFactory = violationFactory,
        )

    override fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.containsNone(chars),
            violationFactory = violationFactory,
        )

    override fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.containsOnly(chars),
            violationFactory = violationFactory,
        )

    override fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.containsRegex(regex),
            violationFactory = violationFactory,
        )

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.contains(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.endsWith(suffix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.lengthBetween(lengthRange),
            violationFactory = violationFactory,
        )

    override fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.lengthNotBetween(lengthRange),
            violationFactory = violationFactory,
        )

    override fun lowerCase(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.lowerCase(),
            violationFactory = violationFactory,
        )

    override fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.matches(regex),
            violationFactory = violationFactory,
        )

    override fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.maxLength(maxLength),
            violationFactory = violationFactory,
        )

    override fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.minLength(minLength),
            violationFactory = violationFactory,
        )

    override fun notBlank(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notBlank(),
            violationFactory = violationFactory,
        )

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notContains(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notContainsRegex(regex),
            violationFactory = violationFactory,
        )

    override fun notEmpty(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notEmpty(),
            violationFactory = violationFactory,
        )

    override fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notMatches(regex),
            violationFactory = violationFactory,
        )

    override fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.notOfLength(length),
            violationFactory = violationFactory,
        )

    override fun numeric(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.numeric(),
            violationFactory = violationFactory,
        )

    override fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.ofLength(length),
            violationFactory = violationFactory,
        )

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: ViolationFactory<String>,
    ): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.startsWith(prefix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun upperCase(violationFactory: ViolationFactory<String>): PredicateRule<String> =
        PredicateRule(
            validationCheck = stringCheckProvider.upperCase(),
            violationFactory = violationFactory,
        )
}
