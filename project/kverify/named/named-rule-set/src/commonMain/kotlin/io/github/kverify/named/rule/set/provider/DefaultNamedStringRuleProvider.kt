package io.github.kverify.named.rule.set.provider

import io.github.kverify.check.set.provider.StringCheckProvider
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedPredicateRule
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class DefaultNamedStringRuleProvider(
    public val stringCheckProvider: StringCheckProvider = StringCheckProvider.Default,
    override val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedStringRuleProvider {
    override fun namedAlphabetic(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.alphabetic(),
            violationFactory = violationFactory,
        )

    override fun namedAlphanumeric(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.alphanumeric(),
            violationFactory = violationFactory,
        )

    override fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.containsAll(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.containsNone(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.containsOnly(chars),
            violationFactory = violationFactory,
        )

    override fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.containsRegex(regex),
            violationFactory = violationFactory,
        )

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.contains(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.endsWith(suffix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.lengthBetween(lengthRange),
            violationFactory = violationFactory,
        )

    override fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.lengthNotBetween(lengthRange),
            violationFactory = violationFactory,
        )

    override fun namedLowerCase(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.lowerCase(),
            violationFactory = violationFactory,
        )

    override fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.matches(regex),
            violationFactory = violationFactory,
        )

    override fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.maxLength(maxLength),
            violationFactory = violationFactory,
        )

    override fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.minLength(minLength),
            violationFactory = violationFactory,
        )

    override fun namedNotBlank(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notBlank(),
            violationFactory = violationFactory,
        )

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notContains(substring, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notContainsRegex(regex),
            violationFactory = violationFactory,
        )

    override fun namedNotEmpty(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notEmpty(),
            violationFactory = violationFactory,
        )

    override fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notMatches(regex),
            violationFactory = violationFactory,
        )

    override fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.notOfLength(length),
            violationFactory = violationFactory,
        )

    override fun namedNumeric(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.numeric(),
            violationFactory = violationFactory,
        )

    override fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.ofLength(length),
            violationFactory = violationFactory,
        )

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
        violationFactory: NamedViolationFactory<String>,
    ): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.startsWith(prefix, ignoreCase),
            violationFactory = violationFactory,
        )

    override fun namedUpperCase(violationFactory: NamedViolationFactory<String>): NamedPredicateRule<String> =
        NamedPredicateRule(
            validationCheck = stringCheckProvider.upperCase(),
            violationFactory = violationFactory,
        )
}
