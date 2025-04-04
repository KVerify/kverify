package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.factory.StringViolationFactory

public open class StringRules(
    public val stringViolationFactory: StringViolationFactory = StringViolationFactory.Default,
) {
    public inner class OfLength(
        public val length: Int,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.ofLength(length, value)
        },
    ) : Rule<String> {
        public constructor(
            length: Int,
            name: String,
        ) : this(
            length = length,
            violationGenerator = { value ->
                stringViolationFactory.ofLength(length, value, name)
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

    public inner class NamedOfLength(
        public val length: Int,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.ofLength(length, value, name)
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

    public inner class NotOfLength(
        public val length: Int,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notOfLength(length, value)
        },
    ) : Rule<String> {
        public constructor(
            length: Int,
            name: String,
        ) : this(
            length = length,
            violationGenerator = { value ->
                stringViolationFactory.notOfLength(length, value, name)
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

    public inner class NamedNotOfLength(
        public val length: Int,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notOfLength(length, value, name)
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

    public inner class MaxLength(
        public val max: Int,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.maxLength(max, value)
        },
    ) : Rule<String> {
        public constructor(
            max: Int,
            name: String,
        ) : this(
            max = max,
            violationGenerator = { value ->
                stringViolationFactory.maxLength(max, value, name)
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

    public inner class NamedMaxLength(
        public val max: Int,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.maxLength(max, value, name)
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

    public inner class MinLength(
        public val min: Int,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.minLength(min, value)
        },
    ) : Rule<String> {
        public constructor(
            min: Int,
            name: String,
        ) : this(
            min = min,
            violationGenerator = { value ->
                stringViolationFactory.minLength(min, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.length >= min,
            ) {
                violationGenerator(value)
            }
        }

        public inner class NamedMinLength(
            public val min: Int,
            public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
                stringViolationFactory.minLength(min, value, name)
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

    public inner class LengthBetween(
        public val range: IntRange,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.lengthBetween(range, value)
        },
    ) : Rule<String> {
        public constructor(
            min: Int,
            max: Int,
            violationGenerator: (String) -> Violation = { value ->
                stringViolationFactory.lengthBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        public constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                stringViolationFactory.lengthBetween(range, value, name)
            },
        )

        public constructor(
            min: Int,
            max: Int,
            name: String,
        ) : this(
            range = min..max,
            violationGenerator = { value ->
                stringViolationFactory.lengthBetween(min..max, value, name)
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

    public inner class NamedLengthBetween(
        public val range: IntRange,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.lengthBetween(range, value, name)
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

    public inner class LengthNotBetween(
        public val range: IntRange,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.lengthNotBetween(range, value)
        },
    ) : Rule<String> {
        public constructor(
            min: Int,
            max: Int,
            violationGenerator: (String) -> Violation = { value ->
                stringViolationFactory.lengthNotBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        public constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                stringViolationFactory.lengthNotBetween(range, value, name)
            },
        )

        public constructor(
            min: Int,
            max: Int,
            name: String,
        ) : this(
            range = min..max,
            violationGenerator = { value ->
                stringViolationFactory.lengthNotBetween(min..max, value, name)
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

    public inner class NamedLengthNotBetween(
        public val range: IntRange,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.lengthNotBetween(range, value, name)
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

    public inner class Contains(
        public val string: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.contains(string, value)
        },
    ) : Rule<String> {
        public constructor(
            string: String,
            ignoreCase: Boolean = false,
            name: String,
        ) : this(
            string = string,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolationFactory.contains(string, value, name)
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

    public inner class NamedContains(
        public val string: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.contains(string, value, name)
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

    public inner class ContainsRegex(
        public val regex: Regex,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.contains(regex, value)
        },
    ) : Rule<String> {
        public constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolationFactory.contains(regex, value, name)
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

    public inner class NamedContainsRegex(
        public val regex: Regex,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.contains(regex, value, name)
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

    public inner class NotContains(
        public val string: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notContains(string, value)
        },
    ) : Rule<String> {
        public constructor(
            string: String,
            ignoreCase: Boolean = false,
            name: String,
        ) : this(
            string = string,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolationFactory.notContains(string, value, name)
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

    public inner class NamedNotContains(
        public val string: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notContains(string, value, name)
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

    public inner class NotContainsRegex(
        public val regex: Regex,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notContains(regex, value)
        },
    ) : Rule<String> {
        public constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolationFactory.notContains(regex, value, name)
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

    public inner class NamedNotContainsRegex(
        public val regex: Regex,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notContains(regex, value, name)
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

    public inner class ContainsAll(
        public val chars: Set<Char>,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.containsAll(chars, value)
        },
    ) : Rule<String> {
        public constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value ->
                stringViolationFactory.containsAll(chars, value, name)
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

    public inner class NamedContainsAll(
        public val chars: Set<Char>,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.containsAll(chars, value, name)
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

    public inner class ContainsOnly(
        public val chars: Set<Char>,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.containsOnly(chars, value)
        },
    ) : Rule<String> {
        public constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value -> stringViolationFactory.containsOnly(chars, value, name) },
        )

        override fun ValidationContext.runValidation(value: String) {
            validate(
                value.all { it in chars },
            ) {
                violationGenerator(value)
            }
        }
    }

    public inner class NamedContainsOnly(
        public val chars: Set<Char>,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.containsOnly(
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

    public inner class ContainsNone(
        public val chars: Set<Char>,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.containsNone(chars, value)
        },
    ) : Rule<String> {
        public constructor(
            chars: Set<Char>,
            name: String,
        ) : this(
            chars = chars,
            violationGenerator = { value ->
                stringViolationFactory.containsNone(chars, value, name)
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

    public inner class NamedContainsNone(
        public val chars: Set<Char>,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.containsNone(chars, value, name)
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

    public inner class Matches(
        public val regex: Regex,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.matches(regex, value)
        },
    ) : Rule<String> {
        public constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolationFactory.matches(regex, value, name)
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

    public inner class NamedMatches(
        public val regex: Regex,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.matches(regex, value, name)
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

    public inner class NotMatches(
        public val regex: Regex,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notMatches(regex, value)
        },
    ) : Rule<String> {
        public constructor(
            regex: Regex,
            name: String,
        ) : this(
            regex = regex,
            violationGenerator = { value ->
                stringViolationFactory.notMatches(regex, value, name)
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

    public inner class NamedNotMatches(
        public val regex: Regex,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notMatches(regex, value, name)
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

    public inner class StartsWith(
        public val prefix: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.startsWith(prefix, value)
        },
    ) : Rule<String> {
        public constructor(
            prefix: String,
            name: String,
            ignoreCase: Boolean = false,
        ) : this(
            prefix = prefix,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolationFactory.startsWith(prefix, value, name)
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

    public inner class NamedStartsWith(
        public val prefix: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.startsWith(prefix, value, name)
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

    public inner class EndsWith(
        public val suffix: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.endsWith(suffix, value)
        },
    ) : Rule<String> {
        public constructor(
            suffix: String,
            name: String,
            ignoreCase: Boolean = false,
        ) : this(
            suffix = suffix,
            ignoreCase = ignoreCase,
            violationGenerator = { value ->
                stringViolationFactory.endsWith(suffix, value, name)
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

    public inner class NamedEndsWith(
        public val suffix: String,
        public val ignoreCase: Boolean = false,
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.endsWith(suffix, value, name)
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

    public inner class Alphabetic(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.alphabetic(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.alphabetic(value, name)
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

    public inner class NamedAlphabetic(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.alphabetic(value, name)
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

    public inner class Numeric(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.numeric(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.numeric(value, name)
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

    public inner class NamedNumeric(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.numeric(value, name)
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

    public inner class Alphanumeric(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.alphanumeric(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.alphanumeric(value, name)
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

    public inner class NamedAlphanumeric(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.alphanumeric(value, name)
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

    public inner class NotBlank(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notBlank(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.notBlank(value, name)
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

    public inner class NamedNotBlank(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notBlank(value, name)
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

    public inner class NotEmpty(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.notEmpty(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.notEmpty(value, name)
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

    public inner class NamedNotEmpty(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.notEmpty(value, name)
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

    public inner class LowerCase(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.lowerCase(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.lowerCase(value, name)
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

    public inner class NamedLowerCase(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.lowerCase(value, name)
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

    public inner class UpperCase(
        public val violationGenerator: (String) -> Violation = { value ->
            stringViolationFactory.upperCase(value)
        },
    ) : Rule<String> {
        public constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                stringViolationFactory.upperCase(value, name)
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

    public inner class NamedUpperCase(
        public val violationGenerator: (NamedValue<String>) -> Violation = { (name, value) ->
            stringViolationFactory.upperCase(value, name)
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

    public companion object : StringRules(
        stringViolationFactory = StringViolationFactory.Default,
    )
}
