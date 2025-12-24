package io.github.kverify.rule.set.provider

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.violation.set.provider.StringViolationProvider

@Suppress("TooManyFunctions")
public interface StringRuleProvider {
    public val stringViolationProvider: StringViolationProvider
        get() = StringViolationProvider.Default

    public fun alphabetic(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.alphabetic(
                    value = value,
                )
            },
    ): Rule<String>

    public fun alphanumeric(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.alphanumeric(
                    value = value,
                )
            },
    ): Rule<String>

    public fun containsAll(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.containsAll(
                    value = value,
                    chars = chars,
                )
            },
    ): Rule<String>

    public fun containsNone(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.containsNone(
                    value = value,
                    chars = chars,
                )
            },
    ): Rule<String>

    public fun containsOnly(
        chars: Iterable<Char>,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.containsOnly(
                    value = value,
                    chars = chars,
                )
            },
    ): Rule<String>

    public fun containsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.containsRegex(
                    value = value,
                    regex = regex,
                )
            },
    ): Rule<String>

    public fun contains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.contains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                )
            },
    ): Rule<String>

    public fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.endsWith(
                    value = value,
                    suffix = suffix,
                    ignoreCase = ignoreCase,
                )
            },
    ): Rule<String>

    public fun lengthBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.lengthBetween(
                    value = value,
                    lengthRange = lengthRange,
                )
            },
    ): Rule<String>

    public fun lengthNotBetween(
        lengthRange: IntRange,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.lengthNotBetween(
                    value = value,
                    lengthRange = lengthRange,
                )
            },
    ): Rule<String>

    public fun lowerCase(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.lowerCase(
                    value = value,
                )
            },
    ): Rule<String>

    public fun matches(
        regex: Regex,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.matches(
                    value = value,
                    regex = regex,
                )
            },
    ): Rule<String>

    public fun maxLength(
        maxLength: Int,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.maxLength(
                    value = value,
                    maxLength = maxLength,
                )
            },
    ): Rule<String>

    public fun minLength(
        minLength: Int,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.minLength(
                    value = value,
                    minLength = minLength,
                )
            },
    ): Rule<String>

    public fun notBlank(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notBlank(
                    value = value,
                )
            },
    ): Rule<String>

    public fun notContains(
        substring: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notContains(
                    value = value,
                    substring = substring,
                    ignoreCase = ignoreCase,
                )
            },
    ): Rule<String>

    public fun notContainsRegex(
        regex: Regex,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notContainsRegex(
                    value = value,
                    regex = regex,
                )
            },
    ): Rule<String>

    public fun notEmpty(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notEmpty(
                    value = value,
                )
            },
    ): Rule<String>

    public fun notMatches(
        regex: Regex,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notMatches(
                    value = value,
                    regex = regex,
                )
            },
    ): Rule<String>

    public fun notOfLength(
        length: Int,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.notOfLength(
                    value = value,
                    length = length,
                )
            },
    ): Rule<String>

    public fun numeric(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.numeric(
                    value = value,
                )
            },
    ): Rule<String>

    public fun ofLength(
        length: Int,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.ofLength(
                    value = value,
                    length = length,
                )
            },
    ): Rule<String>

    public fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.startsWith(
                    value = value,
                    prefix = prefix,
                    ignoreCase = ignoreCase,
                )
            },
    ): Rule<String>

    public fun upperCase(
        violationFactory: ViolationFactory<String> =
            ViolationFactory { value ->
                stringViolationProvider.upperCase(
                    value = value,
                )
            },
    ): Rule<String>
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsAll(
    char: Char,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsAll(
                value = value,
                chars = listOf(char),
            )
        },
): Rule<String> =
    containsAll(
        chars = listOf(char),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsAll(
    chars: String,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsAll(
                value = value,
                chars = chars.asIterable(),
            )
        },
): Rule<String> =
    containsAll(
        chars = chars.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsNone(
    char: Char,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsNone(
                value = value,
                chars = listOf(char),
            )
        },
): Rule<String> =
    containsNone(
        chars = listOf(char),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsNone(
    chars: String,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsNone(
                value = value,
                chars = chars.asIterable(),
            )
        },
): Rule<String> =
    containsNone(
        chars = chars.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsOnly(
    char: Char,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsOnly(
                value = value,
                chars = listOf(char),
            )
        },
): Rule<String> =
    containsOnly(
        chars = listOf(char),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.containsOnly(
    chars: String,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.containsOnly(
                value = value,
                chars = chars.asIterable(),
            )
        },
): Rule<String> =
    containsOnly(
        chars = chars.asIterable(),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.lengthBetween(
    minLength: Int,
    maxLength: Int,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.lengthBetween(
                value = value,
                lengthRange = minLength..maxLength,
            )
        },
): Rule<String> =
    lengthBetween(
        lengthRange = minLength..maxLength,
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringRuleProvider.lengthNotBetween(
    minLength: Int,
    maxLength: Int,
    violationFactory: ViolationFactory<String> =
        ViolationFactory { value ->
            stringViolationProvider.lengthNotBetween(
                value = value,
                lengthRange = minLength..maxLength,
            )
        },
): Rule<String> =
    lengthNotBetween(
        lengthRange = minLength..maxLength,
        violationFactory = violationFactory,
    )
