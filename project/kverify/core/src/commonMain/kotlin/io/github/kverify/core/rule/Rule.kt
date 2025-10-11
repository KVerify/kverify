package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

/**
 * A single validation unit that performs checks on a given value.
 *
 * Implementations define validation logic to be executed within a [ValidationContext].
 * When a rule detects a problem, it should report a [io.github.kverify.core.violation.Violation] through the context.
 */
public fun interface Rule<in T> {
    /**
     * Executes `this` rule for the given [value] within the receiver [ValidationContext].
     */
    public fun ValidationContext.runValidation(value: T)
}

/**
 * Runs `this` [Rule] for the provided [value] using the given [context].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.runValidation(
    context: ValidationContext,
    value: T,
): Unit = context.runValidation(value)

/**
 * Combines `this` rule with another [Rule], producing a composite rule
 * that applies both in sequence.
 *
 * When multiple rules are combined, they are executed in the order they were added.
 */
public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is StackedRule && other is StackedRule -> StackedRule(this.rules + other.rules)
        this is StackedRule -> StackedRule(this.rules + other)
        else -> StackedRule(listOf(this, other))
    }
