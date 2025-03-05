package io.github.kverify.rule.set

import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.StringViolations

@Suppress("TooManyFunctions", "LargeClass")
open class StringRules(
    val stringViolations: StringViolations = StringViolations.Default,
) {
    // Simple value rules with generator
    inline fun ofLength(
        length: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.ofLength(length, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length == length,
            ) {
                violationGenerator(value)
            }
        }

    inline fun notOfLength(
        length: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notOfLength(length, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length != length,
            ) {
                violationGenerator(value)
            }
        }

    inline fun maxLength(
        max: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.maxLength(max, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length <= max,
            ) {
                violationGenerator(value)
            }
        }

    inline fun minLength(
        min: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.minLength(min, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length >= min,
            ) {
                violationGenerator(value)
            }
        }

    inline fun lengthBetween(
        range: IntRange,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthBetween(range, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun lengthNotBetween(
        range: IntRange,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthNotBetween(range, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.length !in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun lengthBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthBetween(min..max, value)
        },
    ): Rule<String> =
        lengthBetween(
            min..max,
            violationGenerator,
        )

    inline fun lengthNotBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthNotBetween(min..max, value)
        },
    ): Rule<String> =
        lengthNotBetween(
            min..max,
            violationGenerator,
        )

    inline fun contains(
        string: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.contains(string, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }

    inline fun contains(
        regex: Regex,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.contains(regex, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }

    inline fun notContains(
        string: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notContains(string, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                !value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }

    inline fun notContains(
        regex: Regex,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notContains(regex, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                !value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }

    inline fun containsAll(
        chars: Set<Char>,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsAll(chars, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                chars.all { it in value },
            ) {
                violationGenerator(value)
            }
        }

    inline fun containsOnly(
        chars: Set<Char>,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsOnly(chars, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it in chars },
            ) {
                violationGenerator(value)
            }
        }

    inline fun containsNone(
        chars: Set<Char>,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsNone(chars, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                chars.none { it in value },
            ) {
                violationGenerator(value)
            }
        }

    inline fun matches(
        regex: Regex,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.matches(regex, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                regex.matches(value),
            ) {
                violationGenerator(value)
            }
        }

    inline fun notMatches(
        regex: Regex,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notMatches(regex, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                !regex.matches(value),
            ) {
                violationGenerator(value)
            }
        }

    inline fun startsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.startsWith(prefix, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.startsWith(prefix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }

    inline fun endsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.endsWith(suffix, value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.endsWith(suffix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }

    inline fun alphabetic(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.alphabetic(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it.isLetter() },
            ) {
                violationGenerator(value)
            }
        }

    inline fun numeric(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.numeric(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it.isDigit() },
            ) {
                violationGenerator(value)
            }
        }

    inline fun alphanumeric(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.alphanumeric(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it.isLetterOrDigit() },
            ) {
                violationGenerator(value)
            }
        }

    inline fun notBlank(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notBlank(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.isNotBlank(),
            ) {
                violationGenerator(value)
            }
        }

    inline fun notEmpty(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.notEmpty(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }

    inline fun lowerCase(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.lowerCase(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it.isLowerCase() },
            ) {
                violationGenerator(value)
            }
        }

    inline fun upperCase(
        crossinline violationGenerator: (String) -> Violation = { value ->
            stringViolations.upperCase(value)
        },
    ): Rule<String> =
        Rule { value ->
            validate(
                value.all { it.isUpperCase() },
            ) {
                violationGenerator(value)
            }
        }

    // Simple value rules with name
    fun ofLength(
        length: Int,
        name: String,
    ): Rule<String> =
        ofLength(length) { value ->
            stringViolations.ofLength(length, value, name)
        }

    fun notOfLength(
        length: Int,
        name: String,
    ): Rule<String> =
        notOfLength(length) { value ->
            stringViolations.notOfLength(length, value, name)
        }

    fun maxLength(
        max: Int,
        name: String,
    ): Rule<String> =
        maxLength(max) { value ->
            stringViolations.maxLength(max, value, name)
        }

    fun minLength(
        min: Int,
        name: String,
    ): Rule<String> =
        minLength(min) { value ->
            stringViolations.minLength(min, value, name)
        }

    fun lengthBetween(
        range: IntRange,
        name: String,
    ): Rule<String> =
        lengthBetween(range) { value ->
            stringViolations.lengthBetween(range, value, name)
        }

    fun lengthBetween(
        min: Int,
        max: Int,
        name: String,
    ): Rule<String> =
        lengthBetween(min..max) { value ->
            stringViolations.lengthBetween(min..max, value, name)
        }

    fun lengthNotBetween(
        range: IntRange,
        name: String,
    ): Rule<String> =
        lengthNotBetween(range) { value ->
            stringViolations.lengthNotBetween(range, value, name)
        }

    fun lengthNotBetween(
        min: Int,
        max: Int,
        name: String,
    ): Rule<String> =
        lengthNotBetween(min..max) { value ->
            stringViolations.lengthNotBetween(min..max, value, name)
        }

    fun contains(
        string: String,
        name: String,
        ignoreCase: Boolean = false,
    ): Rule<String> =
        contains(string, ignoreCase) { value ->
            stringViolations.contains(string, value, name)
        }

    fun contains(
        regex: Regex,
        name: String,
    ): Rule<String> =
        contains(regex) { value ->
            stringViolations.contains(regex, value, name)
        }

    fun notContains(
        string: String,
        name: String,
        ignoreCase: Boolean = false,
    ): Rule<String> =
        notContains(string, ignoreCase) { value ->
            stringViolations.notContains(string, value, name)
        }

    fun notContains(
        regex: Regex,
        name: String,
    ): Rule<String> =
        notContains(regex) { value ->
            stringViolations.notContains(regex, value, name)
        }

    fun containsAll(
        chars: Set<Char>,
        name: String,
    ): Rule<String> =
        containsAll(chars) { value ->
            stringViolations.containsAll(chars, value, name)
        }

    fun containsOnly(
        chars: Set<Char>,
        name: String,
    ): Rule<String> =
        containsOnly(chars) { value ->
            stringViolations.containsOnly(chars, value, name)
        }

    fun containsNone(
        chars: Set<Char>,
        name: String,
    ): Rule<String> =
        containsNone(chars) { value ->
            stringViolations.containsNone(chars, value, name)
        }

    fun matches(
        regex: Regex,
        name: String,
    ): Rule<String> =
        matches(regex) { value ->
            stringViolations.matches(regex, value, name)
        }

    fun notMatches(
        regex: Regex,
        name: String,
    ): Rule<String> =
        notMatches(regex) { value ->
            stringViolations.notMatches(regex, value, name)
        }

    fun startsWith(
        prefix: String,
        name: String,
        ignoreCase: Boolean = false,
    ): Rule<String> =
        startsWith(prefix, ignoreCase) { value ->
            stringViolations.startsWith(prefix, value, name)
        }

    fun endsWith(
        suffix: String,
        name: String,
        ignoreCase: Boolean = false,
    ): Rule<String> =
        endsWith(suffix) { value ->
            stringViolations.endsWith(suffix, value, name)
        }

    fun alphabetic(name: String): Rule<String> =
        alphabetic { value ->
            stringViolations.alphabetic(value, name)
        }

    fun numeric(name: String): Rule<String> =
        numeric { value ->
            stringViolations.numeric(value, name)
        }

    fun alphanumeric(name: String): Rule<String> =
        alphanumeric { value ->
            stringViolations.alphanumeric(value, name)
        }

    fun notBlank(name: String): Rule<String> =
        notBlank { value ->
            stringViolations.notBlank(value, name)
        }

    fun notEmpty(name: String): Rule<String> =
        notEmpty { value ->
            stringViolations.notEmpty(value, name)
        }

    fun lowerCase(name: String): Rule<String> =
        lowerCase { value ->
            stringViolations.lowerCase(value, name)
        }

    fun upperCase(name: String): Rule<String> =
        upperCase { value ->
            stringViolations.upperCase(value, name)
        }

    // Named value rules
    inline fun namedOfLength(
        length: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.ofLength(length, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                ofLength(length) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotOfLength(
        length: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notOfLength(length, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notOfLength(length) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedMaxLength(
        max: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.maxLength(max, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                maxLength(max) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedMinLength(
        min: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.minLength(min, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                minLength(min) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedLengthBetween(
        range: IntRange,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthBetween(range, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                lengthBetween(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedLengthNotBetween(
        range: IntRange,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthNotBetween(range, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                lengthNotBetween(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedLengthBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthBetween(min..max, value, name)
        },
    ): NamedRule<String> =
        namedLengthBetween(
            min..max,
            violationGenerator,
        )

    inline fun namedLengthNotBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthNotBetween(min..max, value, name)
        },
    ): NamedRule<String> =
        namedLengthNotBetween(
            min..max,
            violationGenerator,
        )

    inline fun namedContains(
        string: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.contains(string, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                contains(string, ignoreCase) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedContains(
        regex: Regex,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.contains(regex, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                contains(regex) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotContains(
        string: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notContains(string, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notContains(string, ignoreCase) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotContains(
        regex: Regex,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notContains(regex, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notContains(regex) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedContainsAll(
        chars: Set<Char>,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsAll(chars, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                containsAll(chars) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedContainsOnly(
        chars: Set<Char>,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsOnly(chars, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                containsOnly(chars) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedContainsNone(
        chars: Set<Char>,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsNone(chars, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                containsNone(chars) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedMatches(
        regex: Regex,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.matches(regex, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                matches(regex) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotMatches(
        regex: Regex,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notMatches(regex, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notMatches(regex) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedStartsWith(
        prefix: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.startsWith(prefix, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                startsWith(prefix, ignoreCase) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedEndsWith(
        suffix: String,
        ignoreCase: Boolean = false,
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.endsWith(suffix, value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                endsWith(suffix, ignoreCase) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedAlphabetic(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.alphabetic(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                alphabetic {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNumeric(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.numeric(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                numeric {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedAlphanumeric(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.alphanumeric(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                alphanumeric {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotBlank(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notBlank(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notBlank {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedNotEmpty(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notEmpty(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                notEmpty {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedLowerCase(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lowerCase(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                lowerCase {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun namedUpperCase(
        crossinline violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.upperCase(value, name)
        },
    ): NamedRule<String> =
        NamedRule { namedValue ->
            val rule =
                upperCase {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    companion object : StringRules(
        stringViolations = StringViolations.Default,
    )
}
