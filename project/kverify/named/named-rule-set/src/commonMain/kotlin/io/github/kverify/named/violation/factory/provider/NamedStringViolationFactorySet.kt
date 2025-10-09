package io.github.kverify.named.violation.factory.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public class NamedStringViolationFactorySet(
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedStringViolationFactoryProvider {
    override fun namedAlphabetic(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.alphabetic(
                value = value,
                name = name,
            )
        }

    override fun namedAlphanumeric(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.alphanumeric(
                value = value,
                name = name,
            )
        }

    override fun namedContainsAll(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.containsAll(
                value = value,
                chars = chars,
                name = name,
            )
        }

    override fun namedContainsNone(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.containsNone(
                value = value,
                chars = chars,
                name = name,
            )
        }

    override fun namedContainsOnly(chars: Iterable<Char>): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.containsOnly(
                value = value,
                chars = chars,
                name = name,
            )
        }

    override fun namedContainsRegex(regex: Regex): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.containsRegex(
                value = value,
                regex = regex,
                name = name,
            )
        }

    override fun namedContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.contains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
                name = name,
            )
        }

    override fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.endsWith(
                value = value,
                suffix = suffix,
                ignoreCase = ignoreCase,
                name = name,
            )
        }

    override fun namedLengthBetween(lengthRange: IntRange): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.lengthBetween(
                value = value,
                lengthRange = lengthRange,
                name = name,
            )
        }

    override fun namedLengthNotBetween(lengthRange: IntRange): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.lengthNotBetween(
                value = value,
                lengthRange = lengthRange,
                name = name,
            )
        }

    override fun namedLowerCase(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.lowerCase(
                value = value,
                name = name,
            )
        }

    override fun namedMatches(regex: Regex): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.matches(
                value = value,
                regex = regex,
                name = name,
            )
        }

    override fun namedMaxLength(maxLength: Int): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.maxLength(
                value = value,
                maxLength = maxLength,
                name = name,
            )
        }

    override fun namedMinLength(minLength: Int): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.minLength(
                value = value,
                minLength = minLength,
                name = name,
            )
        }

    override fun namedNotBlank(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notBlank(
                value = value,
                name = name,
            )
        }

    override fun namedNotContains(
        substring: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notContains(
                value = value,
                substring = substring,
                ignoreCase = ignoreCase,
                name = name,
            )
        }

    override fun namedNotContainsRegex(regex: Regex): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notContainsRegex(
                value = value,
                regex = regex,
                name = name,
            )
        }

    override fun namedNotEmpty(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notEmpty(
                value = value,
                name = name,
            )
        }

    override fun namedNotMatches(regex: Regex): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notMatches(
                value = value,
                regex = regex,
                name = name,
            )
        }

    override fun namedNotOfLength(length: Int): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.notOfLength(
                value = value,
                length = length,
                name = name,
            )
        }

    override fun namedNumeric(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.numeric(
                value = value,
                name = name,
            )
        }

    override fun namedOfLength(length: Int): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.ofLength(
                value = value,
                length = length,
                name = name,
            )
        }

    override fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.startsWith(
                value = value,
                prefix = prefix,
                ignoreCase = ignoreCase,
                name = name,
            )
        }

    override fun namedUpperCase(): NamedViolationFactory<String> =
        NamedViolationFactory { (name, value) ->
            stringViolationProvider.upperCase(
                value = value,
                name = name,
            )
        }
}
