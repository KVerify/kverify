package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

public fun interface Rule<in T> {
    public fun ValidationContext.runValidation(value: T)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Rule<T>.runValidation(
    context: ValidationContext,
    value: T,
): Unit = context.runValidation(value)

public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is StackedRule && other is StackedRule -> StackedRule(this.rules + other.rules)
        this is StackedRule -> StackedRule(this.rules + other)
        else -> StackedRule(listOf(this, other))
    }
