package io.github.kverify.check.set.provider

import io.github.kverify.core.rule.predicate.check.ValidationCheck

@Suppress("TooManyFunctions")
public class DefaultStringCheckProvider : StringCheckProvider {
    override fun contains(
        substring: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> =
        ValidationCheck { value ->
            value.contains(substring, ignoreCase)
        }

    override fun notContains(
        substring: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> =
        ValidationCheck { value ->
            !value.contains(substring, ignoreCase)
        }

    override fun containsAll(chars: Iterable<Char>): ValidationCheck<String> =
        ValidationCheck { value ->
            chars.all { it in value }
        }

    override fun containsOnly(chars: Iterable<Char>): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it in chars }
        }

    override fun containsNone(chars: Iterable<Char>): ValidationCheck<String> =
        ValidationCheck { value ->
            chars.none { it in value }
        }

    override fun notEmpty(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.isNotEmpty()
        }

    override fun notBlank(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.isNotBlank()
        }

    override fun startsWith(
        prefix: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> =
        ValidationCheck { value ->
            value.startsWith(prefix, ignoreCase)
        }

    override fun endsWith(
        suffix: String,
        ignoreCase: Boolean,
    ): ValidationCheck<String> =
        ValidationCheck { value ->
            value.endsWith(suffix, ignoreCase)
        }

    override fun matches(regex: Regex): ValidationCheck<String> =
        ValidationCheck { value ->
            value.matches(regex)
        }

    override fun notMatches(regex: Regex): ValidationCheck<String> =
        ValidationCheck { value ->
            !value.matches(regex)
        }

    override fun containsRegex(regex: Regex): ValidationCheck<String> =
        ValidationCheck { value ->
            value.contains(regex)
        }

    override fun notContainsRegex(regex: Regex): ValidationCheck<String> =
        ValidationCheck { value ->
            !value.contains(regex)
        }

    override fun alphabetic(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it.isLetter() }
        }

    override fun alphanumeric(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it.isLetterOrDigit() }
        }

    override fun numeric(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it.isDigit() }
        }

    override fun upperCase(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it.isUpperCase() }
        }

    override fun lowerCase(): ValidationCheck<String> =
        ValidationCheck { value ->
            value.all { it.isLowerCase() }
        }

    override fun ofLength(length: Int): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length == length
        }

    override fun notOfLength(length: Int): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length != length
        }

    override fun minLength(minLength: Int): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length >= minLength
        }

    override fun maxLength(maxLength: Int): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length <= maxLength
        }

    override fun lengthBetween(lengthRange: IntRange): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length in lengthRange
        }

    override fun lengthNotBetween(lengthRange: IntRange): ValidationCheck<String> =
        ValidationCheck { value ->
            value.length !in lengthRange
        }
}
