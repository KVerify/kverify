package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

public interface Rule<in T> {
    public fun execute(
        context: ValidationContext,
        value: T,
    )
}

/**
 * Combines `this` rule with another [Rule], producing a composite rule
 * that applies both in sequence.
 *
 * When multiple rules are combined, they are executed in the order they were added.
 */
public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is RuleList && other is RuleList -> RuleList(this.rules + other.rules)
        this is RuleList -> RuleList(this.rules + other)
        else -> RuleList(this, other)
    }
