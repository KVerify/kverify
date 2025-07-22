package io.github.kverify.violation.set.provider

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason

@Suppress("TooManyFunctions")
public interface StringViolationProvider {
    public fun ofLength(
        length: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have exactly $length characters, but it has ${value.length}.".asViolationReason()

    public fun notOfLength(
        length: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not have exactly $length characters, but it does.".asViolationReason()

    public fun maxLength(
        max: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have at most $max characters, but it has ${value.length}.".asViolationReason()

    public fun minLength(
        min: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have at least $min characters, but it has ${value.length}.".asViolationReason()

    public fun lengthBetween(
        range: IntRange,
        value: String,
        name: String = "string",
    ): Violation = "'$name' length must be within $range, but it has ${value.length}.".asViolationReason()

    public fun lengthNotBetween(
        range: IntRange,
        value: String,
        name: String = "string",
    ): Violation = "'$name' length must not be within $range, but it has ${value.length}.".asViolationReason()

    public fun contains(
        string: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain '$string', but it does not.".asViolationReason()

    public fun containsRegex(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must match the pattern '$regex', but it does not.".asViolationReason()

    public fun notContains(
        subString: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not contain '$subString', but it does.".asViolationReason()

    public fun notContainsRegex(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not match the pattern '$regex', but it does.".asViolationReason()

    public fun containsAll(
        chars: Iterable<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val missingChars = chars.filterNot { it in value }.toList()
        return "'$name' must contain all of the following characters: $missingChars, but some are missing."
            .asViolationReason()
    }

    public fun containsOnly(
        chars: Iterable<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val unexpectedChars = value.filterNot { it in chars }.toList()
        val charsString = chars.joinToString(", ")

        return "'$name' must contain only the following characters: [$charsString], but it contains: $unexpectedChars."
            .asViolationReason()
    }

    public fun containsNone(
        chars: Iterable<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val forbiddenChars = value.filter { it in chars }.toList()

        return "'$name' must not contain any of the following characters: $forbiddenChars, but it does.".asViolationReason()
    }

    public fun matches(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must fully match the pattern '$regex', but it does not.".asViolationReason()

    public fun notMatches(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not match the pattern '$regex', but it does.".asViolationReason()

    public fun startsWith(
        prefix: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must start with '$prefix', but it does not.".asViolationReason()

    public fun endsWith(
        suffix: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must end with '$suffix', but it does not.".asViolationReason()

    public fun alphabetic(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain only alphabetic characters, but it contains invalid characters.".asViolationReason()

    public fun numeric(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain only numeric characters, but it contains invalid characters.".asViolationReason()

    public fun alphanumeric(
        value: String,
        name: String = "string",
    ): Violation =
        "'$name' must contain only alphanumeric characters, but it contains invalid characters."
            .asViolationReason()

    public fun notBlank(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not be blank, but it is.".asViolationReason()

    public fun notEmpty(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not be empty, but it is.".asViolationReason()

    public fun lowerCase(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must be in lowercase, but it contains uppercase characters.".asViolationReason()

    public fun upperCase(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must be in uppercase, but it contains lowercase characters.".asViolationReason()

    public companion object Default : StringViolationProvider
}
