package io.github.kverify.violation.typed.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider
import io.github.kverify.violation.typed.set.string.StringAlphabeticViolation
import io.github.kverify.violation.typed.set.string.StringAlphanumericViolation
import io.github.kverify.violation.typed.set.string.StringContainsAllViolation
import io.github.kverify.violation.typed.set.string.StringContainsNoneViolation
import io.github.kverify.violation.typed.set.string.StringContainsOnlyViolation
import io.github.kverify.violation.typed.set.string.StringContainsRegexViolation
import io.github.kverify.violation.typed.set.string.StringContainsViolation
import io.github.kverify.violation.typed.set.string.StringEndsWithViolation
import io.github.kverify.violation.typed.set.string.StringLengthBetweenViolation
import io.github.kverify.violation.typed.set.string.StringLengthNotBetweenViolation
import io.github.kverify.violation.typed.set.string.StringLowerCaseViolation
import io.github.kverify.violation.typed.set.string.StringMatchesViolation
import io.github.kverify.violation.typed.set.string.StringMaxLengthViolation
import io.github.kverify.violation.typed.set.string.StringMinLengthViolation
import io.github.kverify.violation.typed.set.string.StringNotBlankViolation
import io.github.kverify.violation.typed.set.string.StringNotContainsRegexViolation
import io.github.kverify.violation.typed.set.string.StringNotContainsViolation
import io.github.kverify.violation.typed.set.string.StringNotEmptyViolation
import io.github.kverify.violation.typed.set.string.StringNotMatchesViolation
import io.github.kverify.violation.typed.set.string.StringNotOfLengthViolation
import io.github.kverify.violation.typed.set.string.StringNumericViolation
import io.github.kverify.violation.typed.set.string.StringOfLengthViolation
import io.github.kverify.violation.typed.set.string.StringStartsWithViolation
import io.github.kverify.violation.typed.set.string.StringUpperCaseViolation

@Suppress("TooManyFunctions")
public class StringTypedViolationProvider(
    public val localization: StringViolationLocalizationProvider = StringViolationLocalizationProvider.Default,
) : StringViolationProvider {
    override fun alphabetic(
        value: String,
        name: String?,
    ): Violation =
        StringAlphabeticViolation(
            value = value,
            name = name,
            reason =
                localization.alphabetic(
                    value = value,
                    name = name,
                ),
        )

    override fun alphanumeric(
        value: String,
        name: String?,
    ): Violation =
        StringAlphanumericViolation(
            value = value,
            name = name,
            reason =
                localization.alphanumeric(
                    value = value,
                    name = name,
                ),
        )

    override fun containsAll(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        StringContainsAllViolation(
            value = value,
            chars = chars,
            name = name,
            reason =
                localization.containsAll(
                    value = value,
                    chars = chars,
                    name = name,
                ),
        )

    override fun containsNone(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        StringContainsNoneViolation(
            value = value,
            chars = chars,
            name = name,
            reason =
                localization.containsNone(
                    value = value,
                    chars = chars,
                    name = name,
                ),
        )

    override fun containsOnly(
        value: String,
        chars: Iterable<Char>,
        name: String?,
    ): Violation =
        StringContainsOnlyViolation(
            value = value,
            chars = chars,
            name = name,
            reason =
                localization.containsOnly(
                    value = value,
                    chars = chars,
                    name = name,
                ),
        )

    override fun containsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        StringContainsRegexViolation(
            value = value,
            regex = regex,
            name = name,
            reason =
                localization.containsRegex(
                    value = value,
                    regex = regex,
                    name = name,
                ),
        )

    override fun contains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        StringContainsViolation(
            value = value,
            substring = substring,
            ignoreCase = ignoreCase,
            name = name,
            reason =
                localization.contains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                    name = name,
                ),
        )

    override fun endsWith(
        value: String,
        suffix: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        StringEndsWithViolation(
            value = value,
            suffix = suffix,
            ignoreCase = ignoreCase,
            name = name,
            reason =
                localization.endsWith(
                    value = value,
                    suffix = suffix,
                    ignoreCase = ignoreCase,
                    name = name,
                ),
        )

    override fun lengthBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): Violation =
        StringLengthBetweenViolation(
            value = value,
            lengthRange = lengthRange,
            name = name,
            reason =
                localization.lengthBetween(
                    value = value,
                    lengthRange = lengthRange,
                    name = name,
                ),
        )

    override fun lengthNotBetween(
        value: String,
        lengthRange: IntRange,
        name: String?,
    ): Violation =
        StringLengthNotBetweenViolation(
            value = value,
            lengthRange = lengthRange,
            name = name,
            reason =
                localization.lengthNotBetween(
                    value = value,
                    lengthRange = lengthRange,
                    name = name,
                ),
        )

    override fun lowerCase(
        value: String,
        name: String?,
    ): Violation =
        StringLowerCaseViolation(
            value = value,
            name = name,
            reason =
                localization.lowerCase(
                    value = value,
                    name = name,
                ),
        )

    override fun matches(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        StringMatchesViolation(
            value = value,
            regex = regex,
            name = name,
            reason =
                localization.matches(
                    value = value,
                    regex = regex,
                    name = name,
                ),
        )

    override fun maxLength(
        value: String,
        maxLength: Int,
        name: String?,
    ): Violation =
        StringMaxLengthViolation(
            value = value,
            maxLength = maxLength,
            name = name,
            reason =
                localization.maxLength(
                    value = value,
                    maxLength = maxLength,
                    name = name,
                ),
        )

    override fun minLength(
        value: String,
        minLength: Int,
        name: String?,
    ): Violation =
        StringMinLengthViolation(
            value = value,
            minLength = minLength,
            name = name,
            reason =
                localization.minLength(
                    value = value,
                    minLength = minLength,
                    name = name,
                ),
        )

    override fun notBlank(
        value: String,
        name: String?,
    ): Violation =
        StringNotBlankViolation(
            value = value,
            name = name,
            reason =
                localization.notBlank(
                    value = value,
                    name = name,
                ),
        )

    override fun notContains(
        value: String,
        substring: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        StringNotContainsViolation(
            value = value,
            substring = substring,
            ignoreCase = ignoreCase,
            name = name,
            reason =
                localization.notContains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                    name = name,
                ),
        )

    override fun notContainsRegex(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        StringNotContainsRegexViolation(
            value = value,
            regex = regex,
            name = name,
            reason =
                localization.notContainsRegex(
                    value = value,
                    regex = regex,
                    name = name,
                ),
        )

    override fun notEmpty(
        value: String,
        name: String?,
    ): Violation =
        StringNotEmptyViolation(
            value = value,
            name = name,
            reason =
                localization.notEmpty(
                    value = value,
                    name = name,
                ),
        )

    override fun notMatches(
        value: String,
        regex: Regex,
        name: String?,
    ): Violation =
        StringNotMatchesViolation(
            value = value,
            regex = regex,
            name = name,
            reason =
                localization.notMatches(
                    value = value,
                    regex = regex,
                    name = name,
                ),
        )

    override fun notOfLength(
        value: String,
        length: Int,
        name: String?,
    ): Violation =
        StringNotOfLengthViolation(
            value = value,
            length = length,
            name = name,
            reason =
                localization.notOfLength(
                    value = value,
                    length = length,
                    name = name,
                ),
        )

    override fun numeric(
        value: String,
        name: String?,
    ): Violation =
        StringNumericViolation(
            value = value,
            name = name,
            reason =
                localization.numeric(
                    value = value,
                    name = name,
                ),
        )

    override fun ofLength(
        value: String,
        length: Int,
        name: String?,
    ): Violation =
        StringOfLengthViolation(
            value = value,
            length = length,
            name = name,
            reason =
                localization.ofLength(
                    value = value,
                    length = length,
                    name = name,
                ),
        )

    override fun startsWith(
        value: String,
        prefix: String,
        ignoreCase: Boolean,
        name: String?,
    ): Violation =
        StringStartsWithViolation(
            value = value,
            prefix = prefix,
            ignoreCase = ignoreCase,
            name = name,
            reason =
                localization.startsWith(
                    value = value,
                    prefix = prefix,
                    ignoreCase = ignoreCase,
                    name = name,
                ),
        )

    override fun upperCase(
        value: String,
        name: String?,
    ): Violation =
        StringUpperCaseViolation(
            value = value,
            name = name,
            reason =
                localization.upperCase(
                    value = value,
                    name = name,
                ),
        )

    public companion object {
        public val Default: StringTypedViolationProvider = StringTypedViolationProvider()
    }
}
