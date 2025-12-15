package io.github.kverify.violation.factory.provider

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.localization.StringViolationLocalizationProvider
import io.github.kverify.violation.set.provider.DefaultStringViolationProvider
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class DefaultStringViolationFactoryProvider(
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : StringViolationFactoryProvider {
    public constructor(
        stringViolationLocalizationProvider: StringViolationLocalizationProvider,
    ) : this(
        stringViolationProvider = DefaultStringViolationProvider(stringViolationLocalizationProvider),
    )

    override fun alphabetic(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.alphabetic(
                value = value,
            )
        }

    override fun alphanumeric(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.alphanumeric(
                value = value,
            )
        }

    override fun containsAll(chars: Iterable<Char>): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsAll(
                value = value,
                chars = chars,
            )
        }

    override fun containsNone(chars: Iterable<Char>): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsNone(
                value = value,
                chars = chars,
            )
        }

    override fun containsOnly(chars: Iterable<Char>): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsOnly(
                value = value,
                chars = chars,
            )
        }

    override fun containsRegex(regex: Regex): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsRegex(
                value = value,
                regex = regex,
            )
        }

    override fun contains(
        substring: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.contains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
            )
        }

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.endsWith(
                value = value,
                suffix = suffix,
                ignoreCase = ignoreCase,
            )
        }

    override fun lengthBetween(lengthRange: IntRange): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.lengthBetween(
                value = value,
                lengthRange = lengthRange,
            )
        }

    override fun lengthNotBetween(lengthRange: IntRange): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.lengthNotBetween(
                value = value,
                lengthRange = lengthRange,
            )
        }

    override fun lowerCase(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.lowerCase(
                value = value,
            )
        }

    override fun matches(regex: Regex): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.matches(
                value = value,
                regex = regex,
            )
        }

    override fun maxLength(maxLength: Int): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.maxLength(
                value = value,
                maxLength = maxLength,
            )
        }

    override fun minLength(minLength: Int): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.minLength(
                value = value,
                minLength = minLength,
            )
        }

    override fun notBlank(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notBlank(
                value = value,
            )
        }

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notContains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
            )
        }

    override fun notContainsRegex(regex: Regex): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notContainsRegex(
                value = value,
                regex = regex,
            )
        }

    override fun notEmpty(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notEmpty(
                value = value,
            )
        }

    override fun notMatches(regex: Regex): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notMatches(
                value = value,
                regex = regex,
            )
        }

    override fun notOfLength(length: Int): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.notOfLength(
                value = value,
                length = length,
            )
        }

    override fun numeric(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.numeric(
                value = value,
            )
        }

    override fun ofLength(length: Int): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.ofLength(
                value = value,
                length = length,
            )
        }

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.startsWith(
                value = value,
                prefix = prefix,
                ignoreCase = ignoreCase,
            )
        }

    override fun upperCase(): ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.upperCase(
                value = value,
            )
        }
}
