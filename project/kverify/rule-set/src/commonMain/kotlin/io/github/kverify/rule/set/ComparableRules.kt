package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.factory.ComparableViolationFactory

public open class ComparableRules(
    public val comparableViolationFactory: ComparableViolationFactory = ComparableViolationFactory.Default,
) {
    public inner class EqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.equalTo(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.equalTo(other, value, name)
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

    public inner class NamedEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.equalTo(other, value, name)
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

    public inner class NotEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.notEqualTo(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.notEqualTo(other, value, name)
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

    public inner class NamedNotEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.notEqualTo(other, value, name)
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

    public inner class GreaterThan<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.greaterThan(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.greaterThan(other, value, name)
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

    public inner class NamedGreaterThan<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.greaterThan(other, value, name)
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

    public inner class GreaterThanOrEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.greaterThanOrEqualTo(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.greaterThanOrEqualTo(other, value, name)
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

    public inner class NamedGreaterThanOrEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.greaterThanOrEqualTo(other, value, name)
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

    public inner class LessThan<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.lessThan(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.lessThan(other, value, name)
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

    public inner class NamedLessThan<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.lessThan(other, value, name)
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

    public inner class LessThanOrEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.lessThanOrEqualTo(other, value)
        },
    ) : Rule<T> {
        public constructor(
            other: T,
            name: String,
        ) : this(
            other = other,
            violationGenerator = { value ->
                comparableViolationFactory.lessThanOrEqualTo(other, value, name)
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

    public inner class NamedLessThanOrEqualTo<T : Comparable<T>>(
        public val other: T,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.lessThanOrEqualTo(other, value, name)
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

    public inner class Between<T : Comparable<T>>(
        public val range: ClosedRange<T>,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.between(range, value)
        },
    ) : Rule<T> {
        public constructor(
            min: T,
            max: T,
            violationGenerator: ValueViolationGenerator<T> = { value ->
                comparableViolationFactory.between(min..max, value)
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

    public inner class NamedBetween<T : Comparable<T>>(
        public val range: ClosedRange<T>,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.between(range, value, name)
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

    public inner class NotBetween<T : Comparable<T>>(
        public val range: ClosedRange<T>,
        public val violationGenerator: ValueViolationGenerator<T> = { value ->
            comparableViolationFactory.notBetween(range, value)
        },
    ) : Rule<T> {
        public constructor(
            min: T,
            max: T,
            violationGenerator: ValueViolationGenerator<T> = { value ->
                comparableViolationFactory.notBetween(min..max, value)
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

    public inner class NamedNotBetween<T : Comparable<T>>(
        public val range: ClosedRange<T>,
        public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            comparableViolationFactory.notBetween(range, value, name)
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

    public companion object : ComparableRules(
        comparableViolationFactory = ComparableViolationFactory.Default,
    )
}
