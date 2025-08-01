package io.github.kverify.violation.factory.classbased.set.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringAlphabeticViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringAlphanumericViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringContainsAllViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringContainsNoneViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringContainsOnlyViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringContainsRegexViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringContainsViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringEndsWithViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringLengthBetweenViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringLengthNotBetweenViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringLowerCaseViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringMatchesViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringMaxLengthViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringMinLengthViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotBlankViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotContainsRegexViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotContainsViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotEmptyViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotMatchesViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNotOfLengthViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringNumericViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringOfLengthViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringStartsWithViolationFactory
import io.github.kverify.violation.factory.classbased.set.string.StringUpperCaseViolationFactory
import io.github.kverify.violation.factory.provider.StringViolationFactoryProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class ClassBasedStringViolationFactories(
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : StringViolationFactoryProvider {
    override fun alphabetic(): ViolationFactory<String> =
        StringAlphabeticViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun alphanumeric(): ViolationFactory<String> =
        StringAlphanumericViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun containsAll(chars: Iterable<Char>): ViolationFactory<String> =
        StringContainsAllViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun containsNone(chars: Iterable<Char>): ViolationFactory<String> =
        StringContainsNoneViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun containsOnly(chars: Iterable<Char>): ViolationFactory<String> =
        StringContainsOnlyViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun containsRegex(regex: Regex): ViolationFactory<String> =
        StringContainsRegexViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        StringContainsViolationFactory(
            substring = substring,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        StringEndsWithViolationFactory(
            suffix = suffix,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun lengthBetween(lengthRange: IntRange): ViolationFactory<String> =
        StringLengthBetweenViolationFactory(
            lengthRange = lengthRange,
            stringViolationProvider = stringViolationProvider,
        )

    override fun lengthNotBetween(lengthRange: IntRange): ViolationFactory<String> =
        StringLengthNotBetweenViolationFactory(
            lengthRange = lengthRange,
            stringViolationProvider = stringViolationProvider,
        )

    override fun lowerCase(): ViolationFactory<String> =
        StringLowerCaseViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun matches(regex: Regex): ViolationFactory<String> =
        StringMatchesViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun maxLength(maxLength: Int): ViolationFactory<String> =
        StringMaxLengthViolationFactory(
            maxLength = maxLength,
            stringViolationProvider = stringViolationProvider,
        )

    override fun minLength(minLength: Int): ViolationFactory<String> =
        StringMinLengthViolationFactory(
            minLength = minLength,
            stringViolationProvider = stringViolationProvider,
        )

    override fun notBlank(): ViolationFactory<String> =
        StringNotBlankViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        StringNotContainsViolationFactory(
            substring = substring,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun notContainsRegex(regex: Regex): ViolationFactory<String> =
        StringNotContainsRegexViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun notEmpty(): ViolationFactory<String> =
        StringNotEmptyViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun notMatches(regex: Regex): ViolationFactory<String> =
        StringNotMatchesViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun notOfLength(length: Int): ViolationFactory<String> =
        StringNotOfLengthViolationFactory(
            length = length,
            stringViolationProvider = stringViolationProvider,
        )

    override fun numeric(): ViolationFactory<String> =
        StringNumericViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun ofLength(length: Int): ViolationFactory<String> =
        StringOfLengthViolationFactory(
            length = length,
            stringViolationProvider = stringViolationProvider,
        )

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        StringStartsWithViolationFactory(
            prefix = prefix,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun upperCase(): ViolationFactory<String> =
        StringUpperCaseViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )
}
