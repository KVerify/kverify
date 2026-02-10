package io.github.kverify.core

import kotlin.jvm.JvmInline

public interface Rule<in T> {
    public fun execute(
        scope: ValidationScope,
        value: T,
    )
}

@JvmInline
public value class RuleList<in T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    override fun execute(
        scope: ValidationScope,
        value: T,
    ) {
        for (rule in rules) {
            rule.execute(
                scope = scope,
                value = value,
            )
        }
    }
}

public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is RuleList && other is RuleList -> RuleList(this.rules + other.rules)
        this is RuleList -> RuleList(this.rules + other)
        other is RuleList -> RuleList(listOf(this) + other.rules)
        else -> RuleList(this, other)
    }

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(): RuleList<T> = RuleList(emptyList())

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(rule: Rule<T>): RuleList<T> = RuleList(listOf(rule))

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(vararg rules: Rule<T>): RuleList<T> = RuleList(rules.asList())

public operator fun <T> RuleList<T>.plus(other: Rule<T>): RuleList<T> =
    when (other) {
        is RuleList<T> -> RuleList(rules + other.rules)
        else -> RuleList(rules + other)
    }

public operator fun <T> RuleList<T>.plus(other: RuleList<T>): RuleList<T> =
    RuleList(
        rules + other.rules,
    )
