package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.ComparableViolations

@Suppress("TooManyFunctions")
open class ComparableRules(
    val comparableViolations: ComparableViolations = ComparableViolations.Default,
) {
    inner class EqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.equalTo(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.equalTo(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value == other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.equalTo(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value == other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.notEqualTo(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.notEqualTo(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value != other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.notEqualTo(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value != other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class GreaterThan<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.greaterThan(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.greaterThan(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value > other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedGreaterThan<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.greaterThan(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value > other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class GreaterThanOrEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.greaterThanOrEqualTo(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.greaterThanOrEqualTo(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value >= other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedGreaterThanOrEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.greaterThanOrEqualTo(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value >= other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class LessThan<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.lessThan(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.lessThan(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value < other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedLessThan<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.lessThan(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value < other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class LessThanOrEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.lessThanOrEqualTo(other, value)
        },
    ) : Rule<T> {
        constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolations.lessThanOrEqualTo(other, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value <= other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedLessThanOrEqualTo<T : Comparable<T>>(
        val other: T,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.lessThanOrEqualTo(other, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value <= other,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Between<T : Comparable<T>>(
        val range: ClosedRange<T>,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.between(range, value)
        },
    ) : Rule<T> {
        constructor(
            min: T,
            max: T,
            violationGenerator: (T) -> Violation = { value ->
                comparableViolations.between(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedBetween<T : Comparable<T>>(
        val range: ClosedRange<T>,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.between(range, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotBetween<T : Comparable<T>>(
        val range: ClosedRange<T>,
        val violationGenerator: (T) -> Violation = { value ->
            comparableViolations.notBetween(range, value)
        },
    ) : Rule<T> {
        constructor(
            min: T,
            max: T,
            violationGenerator: (T) -> Violation = { value ->
                comparableViolations.notBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        override fun ValidationContext.runValidation(value: T) {
            validate(
                value !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotBetween<T : Comparable<T>>(
        val range: ClosedRange<T>,
        val violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.notBetween(range, value, name)
        },
    ) : NamedRule<T> {
        override fun ValidationContext.runValidation(value: NamedValue<T>) {
            validate(
                value.value !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    companion object : ComparableRules(
        comparableViolations = ComparableViolations.Default,
    )
}
