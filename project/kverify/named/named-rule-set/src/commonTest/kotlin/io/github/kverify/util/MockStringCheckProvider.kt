package io.github.kverify.util

import io.github.kverify.check.set.provider.StringCheckProvider
import io.github.kverify.core.rule.predicate.check.ValidationCheck

class MockStringCheckProvider : StringCheckProvider {
    override fun alphabetic(): ValidationCheck<String> = MockCheck("alphabetic")

    override fun alphanumeric(): ValidationCheck<String> = MockCheck("alphanumeric")

    override fun containsAll(chars: Iterable<Char>): ValidationCheck<String> = MockCheck("containsAll")

    override fun containsNone(chars: Iterable<Char>): ValidationCheck<String> = MockCheck("containsNone")

    override fun containsOnly(chars: Iterable<Char>): ValidationCheck<String> = MockCheck("containsOnly")

    override fun containsRegex(regex: Regex): ValidationCheck<String> = MockCheck("containsRegex")

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> = MockCheck("contains")

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> = MockCheck("endsWith")

    override fun lengthBetween(lengthRange: IntRange): ValidationCheck<String> = MockCheck("lengthBetween")

    override fun lengthNotBetween(lengthRange: IntRange): ValidationCheck<String> = MockCheck("lengthNotBetween")

    override fun lowerCase(): ValidationCheck<String> = MockCheck("lowerCase")

    override fun matches(regex: Regex): ValidationCheck<String> = MockCheck("matches")

    override fun maxLength(maxLength: Int): ValidationCheck<String> = MockCheck("maxLength")

    override fun minLength(minLength: Int): ValidationCheck<String> = MockCheck("minLength")

    override fun notBlank(): ValidationCheck<String> = MockCheck("notBlank")

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> = MockCheck("notContains")

    override fun notContainsRegex(regex: Regex): ValidationCheck<String> = MockCheck("notContainsRegex")

    override fun notEmpty(): ValidationCheck<String> = MockCheck("notEmpty")

    override fun notMatches(regex: Regex): ValidationCheck<String> = MockCheck("notMatches")

    override fun notOfLength(length: Int): ValidationCheck<String> = MockCheck("notOfLength")

    override fun numeric(): ValidationCheck<String> = MockCheck("numeric")

    override fun ofLength(length: Int): ValidationCheck<String> = MockCheck("ofLength")

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> = MockCheck("startsWith")

    override fun upperCase(): ValidationCheck<String> = MockCheck("upperCase")
}
