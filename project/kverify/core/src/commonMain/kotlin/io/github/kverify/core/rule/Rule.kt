package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

public interface Rule<in T> {
    public fun run(
        context: ValidationContext,
        value: T,
    )
}

public inline fun <T> Rule(crossinline block: ValidationContext.(T) -> Unit): Rule<T> =
    object : Rule<T> {
        override fun run(
            context: ValidationContext,
            value: T,
        ): Unit = context.block(value)
    }

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
