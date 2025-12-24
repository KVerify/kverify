package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public interface NamedStringRuleProvider {
    public val stringViolationProvider: StringViolationProvider
        get() = StringViolationProvider.Default

    public fun namedAlphabetic(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.alphabetic(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedAlphanumeric(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.alphanumeric(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedContainsAll(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.containsAll(
                    value = value,
                    chars = chars,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedContainsNone(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.containsNone(
                    value = value,
                    chars = chars,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedContainsOnly(
        chars: Iterable<Char>,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.containsOnly(
                    value = value,
                    chars = chars,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.containsRegex(
                    value = value,
                    regex = regex,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.contains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.endsWith(
                    value = value,
                    suffix = suffix,
                    ignoreCase = ignoreCase,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedLengthBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.lengthBetween(
                    value = value,
                    lengthRange = lengthRange,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedLengthNotBetween(
        lengthRange: IntRange,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.lengthNotBetween(
                    value = value,
                    lengthRange = lengthRange,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedLowerCase(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.lowerCase(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.matches(
                    value = value,
                    regex = regex,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedMaxLength(
        maxLength: Int,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.maxLength(
                    value = value,
                    maxLength = maxLength,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedMinLength(
        minLength: Int,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.minLength(
                    value = value,
                    minLength = minLength,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotBlank(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notBlank(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notContains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotContainsRegex(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notContainsRegex(
                    value = value,
                    regex = regex,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotEmpty(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notEmpty(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotMatches(
        regex: Regex,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notMatches(
                    value = value,
                    regex = regex,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNotOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.notOfLength(
                    value = value,
                    length = length,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedNumeric(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.numeric(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedOfLength(
        length: Int,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.ofLength(
                    value = value,
                    length = length,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.startsWith(
                    value = value,
                    prefix = prefix,
                    ignoreCase = ignoreCase,
                    name = name,
                )
            },
    ): NamedRule<String>

    public fun namedUpperCase(
        violationFactory: NamedViolationFactory<String> =
            NamedViolationFactory { (name, value) ->
                stringViolationProvider.upperCase(
                    value = value,
                    name = name,
                )
            },
    ): NamedRule<String>
}
