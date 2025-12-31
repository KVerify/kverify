package io.github.kverify.util

import io.github.kverify.core.violation.ViolationReason
import io.github.kverify.violation.set.provider.StringViolationProvider

class MockStringViolationProvider : StringViolationProvider {
    override fun alphabetic(
        value: String,
        name: String?,
    ) = ViolationReason("alphabetic")

    override fun alphanumeric(
        value: String,
        name: String?,
    ) = ViolationReason("alphanumeric")

    override fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ) = ViolationReason("containsAll")

    override fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ) = ViolationReason("containsNone")

    override fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ) = ViolationReason("containsOnly")

    override fun containsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ) = ViolationReason("containsRegex")

    override fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ) = ViolationReason("contains")

    override fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean,
        name: String?,
    ) = ViolationReason("endsWith")

    override fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ) = ViolationReason("lengthBetween")

    override fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ) = ViolationReason("lengthNotBetween")

    override fun lowerCase(
        value: String,
        name: String?,
    ) = ViolationReason("lowerCase")

    override fun matches(
        value: String,
        regex: Regex,
        name: String?,
    ) = ViolationReason("matches")

    override fun maxLength(
        value: String,
        maxLength: Int,
        name: String?,
    ) = ViolationReason("maxLength")

    override fun minLength(
        value: String,
        minLength: Int,
        name: String?,
    ) = ViolationReason("minLength")

    override fun notBlank(
        value: String,
        name: String?,
    ) = ViolationReason("notBlank")

    override fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ) = ViolationReason("notContains")

    override fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ) = ViolationReason("notContainsRegex")

    override fun notEmpty(
        value: String,
        name: String?,
    ) = ViolationReason("notEmpty")

    override fun notMatches(
        value: String,
        regex: Regex,
        name: String?,
    ) = ViolationReason("notMatches")

    override fun notOfLength(
        value: String,
        length: Int,
        name: String?,
    ) = ViolationReason("notOfLength")

    override fun numeric(
        value: String,
        name: String?,
    ) = ViolationReason("numeric")

    override fun ofLength(
        value: String,
        length: Int,
        name: String?,
    ) = ViolationReason("ofLength")

    override fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean,
        name: String?,
    ) = ViolationReason("startsWith")

    override fun upperCase(
        value: String,
        name: String?,
    ) = ViolationReason("upperCase")
}
