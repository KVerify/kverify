package io.github.kverify.rule.set.violation

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation

@Suppress("TooManyFunctions")
interface StringViolations {
    fun ofLength(
        length: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have exactly $length characters, but it has ${value.length}.".asViolation()

    fun notOfLength(
        length: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not have exactly $length characters, but it does.".asViolation()

    fun maxLength(
        max: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have at most $max characters, but it has ${value.length}.".asViolation()

    fun minLength(
        min: Int,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must have at least $min characters, but it has ${value.length}.".asViolation()

    fun lengthBetween(
        range: IntRange,
        value: String,
        name: String = "string",
    ): Violation = "'$name' length must be within $range, but it has ${value.length}.".asViolation()

    fun lengthNotBetween(
        range: IntRange,
        value: String,
        name: String = "string",
    ): Violation = "'$name' length must not be within $range, but it has ${value.length}.".asViolation()

    fun contains(
        string: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain '$string', but it does not.".asViolation()

    fun contains(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must match the pattern '$regex', but it does not.".asViolation()

    fun notContains(
        string: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not contain '$string', but it does.".asViolation()

    fun notContains(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not match the pattern '$regex', but it does.".asViolation()

    fun containsAll(
        chars: Set<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val missingChars = chars.filterNot { it in value }.toList()
        return "'$name' must contain all of the following characters: $missingChars, but some are missing."
            .asViolation()
    }

    fun containsOnly(
        chars: Set<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val unexpectedChars = value.filterNot { it in chars }.toList()
        val charsString = chars.joinToString(", ")

        return "'$name' must contain only the following characters: [$charsString], but it contains: $unexpectedChars."
            .asViolation()
    }

    fun containsNone(
        chars: Set<Char>,
        value: String,
        name: String = "string",
    ): Violation {
        val forbiddenChars = value.filter { it in chars }.toList()

        return "'$name' must not contain any of the following characters: $forbiddenChars, but it does.".asViolation()
    }

    fun matches(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must fully match the pattern '$regex', but it does not.".asViolation()

    fun notMatches(
        regex: Regex,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not match the pattern '$regex', but it does.".asViolation()

    fun startsWith(
        prefix: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must start with '$prefix', but it does not.".asViolation()

    fun endsWith(
        suffix: String,
        value: String,
        name: String = "string",
    ): Violation = "'$name' must end with '$suffix', but it does not.".asViolation()

    fun alphabetic(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain only alphabetic characters, but it contains invalid characters.".asViolation()

    fun numeric(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must contain only numeric characters, but it contains invalid characters.".asViolation()

    fun alphanumeric(
        value: String,
        name: String = "string",
    ): Violation =
        "'$name' must contain only alphanumeric characters, but it contains invalid characters."
            .asViolation()

    fun notBlank(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not be blank, but it is.".asViolation()

    fun notEmpty(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must not be empty, but it is.".asViolation()

    fun lowerCase(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must be in lowercase, but it contains uppercase characters.".asViolation()

    fun upperCase(
        value: String,
        name: String = "string",
    ): Violation = "'$name' must be in uppercase, but it contains lowercase characters.".asViolation()

    companion object Default : StringViolations
}
