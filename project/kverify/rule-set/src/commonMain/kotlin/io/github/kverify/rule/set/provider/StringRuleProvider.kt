package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider

@Suppress("TooManyFunctions")
public interface StringRuleProvider {
    public val stringViolationFactoryProvider: StringViolationFactoryProvider
        get() = StringViolationFactoryProvider.Default

    public fun alphabetic(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.alphabetic()): Rule<String>

    public fun alphanumeric(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.alphanumeric()): Rule<String>

    public fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.containsAll(chars),
    ): Rule<String>

    public fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.containsNone(chars),
    ): Rule<String>

    public fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.containsOnly(chars),
    ): Rule<String>

    public fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.containsRegex(regex),
    ): Rule<String>

    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.contains(substring, ignoreCase),
    ): Rule<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.endsWith(suffix, ignoreCase),
    ): Rule<String>

    public fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.lengthBetween(lengthRange),
    ): Rule<String>

    public fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.lengthNotBetween(lengthRange),
    ): Rule<String>

    public fun lowerCase(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.lowerCase()): Rule<String>

    public fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.matches(regex),
    ): Rule<String>

    public fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.maxLength(maxLength),
    ): Rule<String>

    public fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.minLength(minLength),
    ): Rule<String>

    public fun notBlank(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notBlank()): Rule<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notContains(substring, ignoreCase),
    ): Rule<String>

    public fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notContainsRegex(regex),
    ): Rule<String>

    public fun notEmpty(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notEmpty()): Rule<String>

    public fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notMatches(regex),
    ): Rule<String>

    public fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.notOfLength(length),
    ): Rule<String>

    public fun numeric(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.numeric()): Rule<String>

    public fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.ofLength(length),
    ): Rule<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.startsWith(prefix, ignoreCase),
    ): Rule<String>

    public fun upperCase(violationFactory: ViolationFactory<String> = stringViolationFactoryProvider.upperCase()): Rule<String>
}
