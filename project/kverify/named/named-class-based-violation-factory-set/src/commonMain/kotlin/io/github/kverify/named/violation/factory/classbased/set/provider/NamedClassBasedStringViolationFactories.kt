package io.github.kverify.named.violation.factory.classbased.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringAlphabeticViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringAlphanumericViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringContainsAllViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringContainsNoneViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringContainsOnlyViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringContainsRegexViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringContainsViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringEndsWithViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringLengthBetweenViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringLengthNotBetweenViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringLowerCaseViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringMatchesViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringMaxLengthViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringMinLengthViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotBlankViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotContainsRegexViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotContainsViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotEmptyViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotMatchesViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNotOfLengthViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringNumericViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringOfLengthViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringStartsWithViolationFactory
import io.github.kverify.named.violation.factory.classbased.set.string.NamedStringUpperCaseViolationFactory
import io.github.kverify.named.violation.factory.provider.NamedStringViolationFactoryProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedClassBasedStringViolationFactories(
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedStringViolationFactoryProvider {
    override fun namedAlphabetic(): NamedViolationFactory<String> =
        NamedStringAlphabeticViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedAlphanumeric(): NamedViolationFactory<String> =
        NamedStringAlphanumericViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedContainsAll(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedStringContainsAllViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedContainsNone(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedStringContainsNoneViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedContainsOnly(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedStringContainsOnlyViolationFactory(
            chars = chars,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedContainsRegex(regex: Regex): NamedViolationFactory<String> =
        NamedStringContainsRegexViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedStringContainsViolationFactory(
            substring = substring,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedStringEndsWithViolationFactory(
            suffix = suffix,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedLengthBetween(lengthRange: IntRange): NamedViolationFactory<String> =
        NamedStringLengthBetweenViolationFactory(
            lengthRange = lengthRange,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedLengthNotBetween(lengthRange: IntRange): NamedViolationFactory<String> =
        NamedStringLengthNotBetweenViolationFactory(
            lengthRange = lengthRange,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedLowerCase(): NamedViolationFactory<String> =
        NamedStringLowerCaseViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedMatches(regex: Regex): NamedViolationFactory<String> =
        NamedStringMatchesViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedMaxLength(maxLength: Int): NamedViolationFactory<String> =
        NamedStringMaxLengthViolationFactory(
            maxLength = maxLength,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedMinLength(minLength: Int): NamedViolationFactory<String> =
        NamedStringMinLengthViolationFactory(
            minLength = minLength,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotBlank(): NamedViolationFactory<String> =
        NamedStringNotBlankViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedStringNotContainsViolationFactory(
            substring = substring,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotContainsRegex(regex: Regex): NamedViolationFactory<String> =
        NamedStringNotContainsRegexViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotEmpty(): NamedViolationFactory<String> =
        NamedStringNotEmptyViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotMatches(regex: Regex): NamedViolationFactory<String> =
        NamedStringNotMatchesViolationFactory(
            regex = regex,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNotOfLength(length: Int): NamedViolationFactory<String> =
        NamedStringNotOfLengthViolationFactory(
            length = length,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedNumeric(): NamedViolationFactory<String> =
        NamedStringNumericViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedOfLength(length: Int): NamedViolationFactory<String> =
        NamedStringOfLengthViolationFactory(
            length = length,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedStringStartsWithViolationFactory(
            prefix = prefix,
            ignoreCase = ignoreCase,
            stringViolationProvider = stringViolationProvider,
        )

    override fun namedUpperCase(): NamedViolationFactory<String> =
        NamedStringUpperCaseViolationFactory(
            stringViolationProvider = stringViolationProvider,
        )
}
