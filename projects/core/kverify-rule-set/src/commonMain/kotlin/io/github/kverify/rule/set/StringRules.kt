package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.StringViolations

open class StringRules(
    val stringViolations: StringViolations = StringViolations.Default,
) {
    inner class OfLength(
        val length: Int,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.ofLength(length, value)
        },
    ) : Rule<String> {
        constructor(
            length: Int,
            name: String,
        ) : this(
            length = length,
            violationGenerator = { value ->
                stringViolations.ofLength(length, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length == length,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedOfLength(
        val length: Int,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.ofLength(length, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.length == length,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotOfLength(
        val length: Int,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notOfLength(length, value)
        },
    ) : Rule<String> {
        constructor(
            length: Int,
            name: String,
        ) : this(
            length = length,
            violationGenerator = { value ->
                stringViolations.notOfLength(length, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length != length,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotOfLength(
        val length: Int,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notOfLength(length, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.length != length,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class MaxLength(
        val max: Int,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.maxLength(max, value)
        },
    ) : Rule<String> {
        constructor(
            max: Int,
            name: String,
        ) : this(
            max = max,
            violationGenerator = { value ->
                stringViolations.maxLength(max, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length <= max,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedMaxLength(
        val max: Int,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.maxLength(max, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.length <= max,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class MinLength(
        val min: Int,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.minLength(min, value)
        },
    ) : Rule<String> {
        constructor(
            min: Int,
            name: String,
        ) : this(
            min = min,
            violationGenerator = { value ->
                stringViolations.minLength(min, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length >= min,
            ) {
                violationGenerator(value)
            }
        }

        inner class NamedMinLength(
            val min: Int,
            val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
                stringViolations.minLength(min, value, name)
            },
        ) : NamedRule<String> {
            override fun ValidationContext.runValidation(value: NamedValue<String>) {
                validate(
                    value.value.length >= min,
                ) {
                    violationGenerator(value)
                }
            }
        }
    }

    inner class LengthBetween(
        val range: IntRange,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthBetween(range, value)
        },
    ) : Rule<String> {
        constructor(
            min: Int,
            max: Int,
            violationGenerator: (String) -> Violation = { value ->
                stringViolations.lengthBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                stringViolations.lengthBetween(range, value, name)
            },
        )

        constructor(
            min: Int,
            max: Int,
            name: String,
        ) : this(
            range = min..max,
            violationGenerator = { value ->
                stringViolations.lengthBetween(min..max, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedLengthBetween(
        val range: IntRange,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthBetween(range, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.length in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class LengthNotBetween(
        val range: IntRange,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.lengthNotBetween(range, value)
        },
    ) : Rule<String> {
        constructor(
            min: Int,
            max: Int,
            violationGenerator: (String) -> Violation = { value ->
                stringViolations.lengthNotBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                stringViolations.lengthNotBetween(range, value, name)
            },
        )

        constructor(
            min: Int,
            max: Int,
            name: String,
        ) : this(
            range = min..max,
            violationGenerator = { value ->
                stringViolations.lengthNotBetween(min..max, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedLengthNotBetween(
        val range: IntRange,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lengthNotBetween(range, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.length !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Contains(
        val string: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.contains(string, value)
        },
    ) : Rule<String> {
        constructor(
            string: String,
            ignoreCase: Boolean = false,
            name: String,
        ) : this(
            string = string,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolations.contains(string, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContains(
        val string: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.contains(string, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsRegex(
        val regex: Regex,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.contains(regex, value)
        },
    ) : Rule<String> {
        constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolations.contains(regex, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsRegex(
        val regex: Regex,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.contains(regex, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotContains(
        val string: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notContains(string, value)
        },
    ) : Rule<String> {
        constructor(
            string: String,
            ignoreCase: Boolean = false,
            name: String,
        ) : this(
            string = string,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolations.notContains(string, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                !value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotContains(
        val string: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notContains(string, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                !value.value.contains(string, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotContainsRegex(
        val regex: Regex,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notContains(regex, value)
        },
    ) : Rule<String> {
        constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolations.notContains(regex, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                !value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotContainsRegex(
        val regex: Regex,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notContains(regex, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                !value.value.contains(regex),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsAll(
        val chars: Set<Char>,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsAll(chars, value)
        },
    ) : Rule<String> {
        constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value ->
                stringViolations.containsAll(chars, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                chars.all { it in value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsAll(
        val chars: Set<Char>,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsAll(chars, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                chars.all { it in value.value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsOnly(
        val chars: Set<Char>,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsOnly(chars, value)
        },
    ) : Rule<String> {
        constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value -> stringViolations.containsOnly(chars, value, name) },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it in chars },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsOnly(
        val chars: Set<Char>,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsOnly(
                chars,
                value,
                name,
            )
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it in chars },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsNone(
        val chars: Set<Char>,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.containsNone(chars, value)
        },
    ) : Rule<String> {
        constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value ->
                stringViolations.containsNone(chars, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                chars.none { it in value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsNone(
        val chars: Set<Char>,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.containsNone(chars, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                chars.none { it in value.value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Matches(
        val regex: Regex,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.matches(regex, value)
        },
    ) : Rule<String> {
        constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolations.matches(regex, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                regex.matches(value),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedMatches(
        val regex: Regex,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.matches(regex, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                regex.matches(value.value),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotMatches(
        val regex: Regex,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notMatches(regex, value)
        },
    ) : Rule<String> {
        constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolations.notMatches(regex, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                !regex.matches(value),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotMatches(
        val regex: Regex,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notMatches(regex, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                !regex.matches(value.value),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class StartsWith(
        val prefix: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.startsWith(prefix, value)
        },
    ) : Rule<String> {
        constructor(
            prefix: String,
            name: String,
            ignoreCase: Boolean = false,
        ) : this(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolations.startsWith(prefix, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.startsWith(prefix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedStartsWith(
        val prefix: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.startsWith(prefix, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.startsWith(prefix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class EndsWith(
        val suffix: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.endsWith(suffix, value)
        },
    ) : Rule<String> {
        constructor(
            suffix: String,
            name: String,
            ignoreCase: Boolean = false,
        ) : this(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolations.endsWith(suffix, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.endsWith(suffix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedEndsWith(
        val suffix: String,
        val ignoreCase: Boolean = false,
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.endsWith(suffix, value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.endsWith(suffix, ignoreCase),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Alphabetic(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.alphabetic(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.alphabetic(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it.isLetter() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedAlphabetic(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.alphabetic(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it.isLetter() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Numeric(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.numeric(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.numeric(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it.isDigit() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNumeric(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.numeric(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it.isDigit() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Alphanumeric(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.alphanumeric(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.alphanumeric(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it.isLetterOrDigit() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedAlphanumeric(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.alphanumeric(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it.isLetterOrDigit() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotBlank(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notBlank(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.notBlank(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.isNotBlank(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotBlank(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notBlank(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.isNotBlank(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotEmpty(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.notEmpty(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.notEmpty(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotEmpty(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.notEmpty(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class LowerCase(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.lowerCase(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.lowerCase(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it.isLowerCase() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedLowerCase(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.lowerCase(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it.isLowerCase() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class UpperCase(
        val violationGenerator: (String) -> Violation = { value ->
            stringViolations.upperCase(value)
        },
    ) : Rule<String> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolations.upperCase(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it.isUpperCase() },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedUpperCase(
        val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolations.upperCase(value, name)
        },
    ) : NamedRule<String> {
        override fun ValidationContext.runValidation(value: NamedValue<String>) {
            validate(
                value.value.all { it.isUpperCase() },
            ) {
                violationGenerator(value)
            }
        }
    }

    companion object : StringRules(
        stringViolations = StringViolations.Default,
    )
}
