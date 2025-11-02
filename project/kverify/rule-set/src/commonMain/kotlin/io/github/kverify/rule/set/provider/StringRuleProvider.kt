package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory

@Suppress("TooManyFunctions")
public interface StringRuleProvider {
    public fun alphabetic(): Rule<String>

    public fun alphanumeric(): Rule<String>

    public fun containsAll(chars: Iterable<Char>): Rule<String>

    public fun containsNone(chars: Iterable<Char>): Rule<String>

    public fun containsOnly(chars: Iterable<Char>): Rule<String>

    public fun containsRegex(regex: Regex): Rule<String>

    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
    ): Rule<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
    ): Rule<String>

    public fun lengthBetween(lengthRange: IntRange): Rule<String>

    public fun lengthNotBetween(lengthRange: IntRange): Rule<String>

    public fun lowerCase(): Rule<String>

    public fun matches(regex: Regex): Rule<String>

    public fun maxLength(maxLength: Int): Rule<String>

    public fun minLength(minLength: Int): Rule<String>

    public fun notBlank(): Rule<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
    ): Rule<String>

    public fun notContainsRegex(regex: Regex): Rule<String>

    public fun notEmpty(): Rule<String>

    public fun notMatches(regex: Regex): Rule<String>

    public fun notOfLength(length: Int): Rule<String>

    public fun numeric(): Rule<String>

    public fun ofLength(length: Int): Rule<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
    ): Rule<String>

    public fun upperCase(): Rule<String>
}

@Suppress("TooManyFunctions")
public interface StringRuleWithFactoryProvider {
    public fun alphabetic(violationFactory: ViolationFactory<String>): Rule<String>

    public fun alphanumeric(violationFactory: ViolationFactory<String>): Rule<String>

    public fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun lowerCase(violationFactory: ViolationFactory<String>): Rule<String>

    public fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun notBlank(violationFactory: ViolationFactory<String>): Rule<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun notEmpty(violationFactory: ViolationFactory<String>): Rule<String>

    public fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun numeric(violationFactory: ViolationFactory<String>): Rule<String>

    public fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String>,
    ): Rule<String>

    public fun upperCase(violationFactory: ViolationFactory<String>): Rule<String>
}
