package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

public interface Rule<in T> {
    public fun execute(
        context: ValidationContext,
        value: T,
    )
}

public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is RuleList && other is RuleList -> RuleList(this.rules + other.rules)
        this is RuleList -> RuleList(this.rules + other)
        else -> RuleList(this, other)
    }
