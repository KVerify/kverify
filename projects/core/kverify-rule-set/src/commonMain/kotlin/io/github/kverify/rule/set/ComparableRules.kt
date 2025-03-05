package io.github.kverify.rule.set

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
    // Simple value rules with generator
    inline fun <T : Comparable<T>> equalTo(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.equalTo(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value == other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> notEqualTo(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.notEqualTo(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value != other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> greaterThan(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.greaterThan(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value > other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.greaterThanOrEqualTo(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value >= other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> lessThan(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.lessThan(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value < other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.lessThanOrEqualTo(other, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value <= other,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.between(range, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> between(
        lower: T,
        upper: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.between(lower..upper, value)
        },
    ): Rule<T> =
        between(
            lower..upper,
            violationGenerator,
        )

    inline fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.notBetween(range, value)
        },
    ): Rule<T> =
        Rule { value ->
            validate(
                value !in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T : Comparable<T>> notBetween(
        lower: T,
        upper: T,
        crossinline violationGenerator: (T) -> Violation = { value ->
            comparableViolations.notBetween(lower..upper, value)
        },
    ): Rule<T> =
        notBetween(
            lower..upper,
            violationGenerator,
        )

    // Simple value rule with name
    fun <T : Comparable<T>> equalTo(
        other: T,
        name: String,
    ): Rule<T> =
        equalTo(other) { value ->
            comparableViolations.equalTo(other, value, name)
        }

    fun <T : Comparable<T>> notEqualTo(
        other: T,
        name: String,
    ): Rule<T> =
        notEqualTo(other) { value ->
            comparableViolations.notEqualTo(other, value, name)
        }

    fun <T : Comparable<T>> greaterThan(
        other: T,
        name: String,
    ): Rule<T> =
        greaterThan(other) { value ->
            comparableViolations.greaterThan(other, value, name)
        }

    fun <T : Comparable<T>> greaterThanOrEqualTo(
        other: T,
        name: String,
    ): Rule<T> =
        greaterThanOrEqualTo(other) { value ->
            comparableViolations.greaterThanOrEqualTo(other, value, name)
        }

    fun <T : Comparable<T>> lessThan(
        other: T,
        name: String,
    ): Rule<T> =
        lessThan(other) { value ->
            comparableViolations.lessThan(other, value, name)
        }

    fun <T : Comparable<T>> lessThanOrEqualTo(
        other: T,
        name: String,
    ): Rule<T> =
        lessThanOrEqualTo(other) { value ->
            comparableViolations.lessThanOrEqualTo(other, value, name)
        }

    fun <T : Comparable<T>> between(
        range: ClosedRange<T>,
        name: String,
    ): Rule<T> =
        between(range) { value ->
            comparableViolations.between(range, value, name)
        }

    fun <T : Comparable<T>> between(
        lower: T,
        upper: T,
        name: String,
    ): Rule<T> =
        between(lower, upper) { value ->
            comparableViolations.between(lower..upper, value, name)
        }

    fun <T : Comparable<T>> notBetween(
        range: ClosedRange<T>,
        name: String,
    ): Rule<T> =
        notBetween(range) { value ->
            comparableViolations.notBetween(range, value, name)
        }

    fun <T : Comparable<T>> notBetween(
        lower: T,
        upper: T,
        name: String,
    ): Rule<T> =
        notBetween(lower, upper) { value ->
            comparableViolations.notBetween(lower..upper, value, name)
        }

    // Named value rules
    inline fun <T : Comparable<T>> namedEqualTo(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.equalTo(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                equalTo(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedNotEqualTo(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.notEqualTo(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                notEqualTo(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedGreaterThan(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.greaterThan(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                greaterThan(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedGreaterThanOrEqualTo(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.greaterThanOrEqualTo(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                greaterThanOrEqualTo(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedLessThan(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.lessThan(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                lessThan(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedLessThanOrEqualTo(
        other: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.lessThanOrEqualTo(other, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                lessThanOrEqualTo(other) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedBetween(
        range: ClosedRange<T>,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.between(range, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                between(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedBetween(
        lower: T,
        upper: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.between(lower..upper, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                between(lower, upper) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedNotBetween(
        range: ClosedRange<T>,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.notBetween(range, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                notBetween(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T : Comparable<T>> namedNotBetween(
        lower: T,
        upper: T,
        crossinline violationGenerator: (NamedValue<T>) -> Violation = { (name, value) ->
            comparableViolations.notBetween(lower..upper, value, name)
        },
    ): NamedRule<T> =
        NamedRule { namedValue ->
            val rule =
                notBetween(lower, upper) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    companion object : ComparableRules(
        comparableViolations = ComparableViolations.Default,
    )
}
