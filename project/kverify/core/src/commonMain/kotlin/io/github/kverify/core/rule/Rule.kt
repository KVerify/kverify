package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

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
    when {
        this is StackedRule && other is StackedRule -> StackedRule(this.rules + other.rules)
        this is StackedRule -> StackedRule(this.rules + other)
        else -> StackedRule(listOf(this, other))
    }
