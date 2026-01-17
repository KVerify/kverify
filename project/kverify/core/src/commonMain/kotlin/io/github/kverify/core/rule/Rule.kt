package io.github.kverify.core.rule

import io.github.kverify.core.scope.ValidationScope

public interface Rule<in T> {
    public fun execute(
        scope: ValidationScope,
        value: T,
    )
}

public inline fun <T> Rule(crossinline execute: ValidationScope.(T) -> Unit): Rule<T> =
    object : Rule<T> {
        override fun execute(
            scope: ValidationScope,
            value: T,
        ): Unit = execute(scope, value)
    }

public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is RuleList && other is RuleList -> RuleList(this.rules + other.rules)
        this is RuleList -> RuleList(this.rules + other)
        other is RuleList -> RuleList(listOf(this) + other.rules)
        else -> RuleList(this, other)
    }
