package io.github.kverify.core.model

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.violation.Violation

/**
 * A validation [Rule] for a [NamedValue].
 */
public typealias NamedRule<T> = Rule<NamedValue<T>>

/**
 * Represents a validation rule for a value of type [T].
 */
public fun interface Rule<T> {
    /**
     * Runs validation for the given [value] within this [ValidationContext].
     */
    public fun ValidationContext.runValidation(value: T)
}

/**
 * Runs this [Rule] within the given [context] for the specified [value].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.runValidation(
    context: ValidationContext,
    value: T,
): Unit = context.runValidation(value)

/**
 * Combines this [Rule] with [other], applying both sequentially.
 */
public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    Rule validationContext@{ value ->
        this@plus.runValidation(
            this@validationContext,
            value,
        )

        other.runValidation(
            this@validationContext,
            value,
        )
    }

/**
 * Creates a [Rule] that executes [ValidationContext.validate]
 * with the given [predicate] and [violationGenerator].
 */
public inline fun <T> Rule(
    crossinline predicate: (T) -> Boolean,
    crossinline violationGenerator: (T) -> Violation,
): Rule<T> =
    Rule { value ->
        validate(
            predicate(value),
        ) {
            violationGenerator(value)
        }
    }
