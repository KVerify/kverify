package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider

@Suppress("TooManyFunctions")
public class StringViolations(
    private val stringViolationLocalizationProvider: StringViolationLocalizationProvider =
        StringViolationLocalizationProvider.Default,
) : StringViolationProvider {
    override fun alphabetic(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .alphabetic(
                value = value,
                name = name,
            ).asViolationReason()

    override fun alphanumeric(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .alphanumeric(
                value = value,
                name = name,
            ).asViolationReason()

    override fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .containsAll(
                value = value,
                chars = chars,
                name = name,
            ).asViolationReason()

    override fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .containsNone(
                value = value,
                chars = chars,
                name = name,
            ).asViolationReason()

    override fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .containsOnly(
                value = value,
                chars = chars,
                name = name,
            ).asViolationReason()

    override fun containsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .containsRegex(
                value = value,
                regex = regex,
                name = name,
            ).asViolationReason()

    override fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .contains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
                name = name,
            ).asViolationReason()

    override fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .endsWith(
                value = value,
                suffix = suffix,
                ignoreCase = ignoreCase,
                name = name,
            ).asViolationReason()

    override fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .lengthBetween(
                value = value,
                lengthRange = lengthRange,
                name = name,
            ).asViolationReason()

    override fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .lengthNotBetween(
                value = value,
                lengthRange = lengthRange,
                name = name,
            ).asViolationReason()

    override fun lowerCase(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .lowerCase(
                value = value,
                name = name,
            ).asViolationReason()

    override fun matches(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .matches(
                value = value,
                regex = regex,
                name = name,
            ).asViolationReason()

    override fun maxLength(
        value: String,
        maxLength: Int,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .maxLength(
                value = value,
                maxLength = maxLength,
                name = name,
            ).asViolationReason()

    override fun minLength(
        value: String,
        minLength: Int,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .minLength(
                value = value,
                minLength = minLength,
                name = name,
            ).asViolationReason()

    override fun notBlank(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notBlank(
                value = value,
                name = name,
            ).asViolationReason()

    override fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notContains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
                name = name,
            ).asViolationReason()

    override fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notContainsRegex(
                value = value,
                regex = regex,
                name = name,
            ).asViolationReason()

    override fun notEmpty(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notEmpty(
                value = value,
                name = name,
            ).asViolationReason()

    override fun notMatches(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notMatches(
                value = value,
                regex = regex,
                name = name,
            ).asViolationReason()

    override fun notOfLength(
        value: String,
        length: Int,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .notOfLength(
                value = value,
                length = length,
                name = name,
            ).asViolationReason()

    override fun numeric(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .numeric(
                value = value,
                name = name,
            ).asViolationReason()

    override fun ofLength(
        value: String,
        length: Int,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .ofLength(
                value = value,
                length = length,
                name = name,
            ).asViolationReason()

    override fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .startsWith(
                value = value,
                prefix = prefix,
                ignoreCase = ignoreCase,
                name = name,
            ).asViolationReason()

    override fun upperCase(
        value: String,
        name: String?,
    ): Violation =
        stringViolationLocalizationProvider
            .upperCase(
                value = value,
                name = name,
            ).asViolationReason()
}
