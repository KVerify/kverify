package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

/**
 * Composite [Rule] that executes a list of inner [rules] sequentially.
 */
@JvmInline
public value class RuleList<in T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    override fun execute(
        context: ValidationContext,
        value: T,
    ) {
        for (rule in rules) {
            rule.execute(
                context = context,
                value = value,
            )
        }
    }
}

/**
 * Creates an empty [RuleList].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(): RuleList<T> = RuleList(emptyList())

/**
 * Creates a [RuleList] containing a single [rule].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(rule: Rule<T>): RuleList<T> = RuleList(listOf(rule))

/**
 * Creates a [RuleList] from the provided [rules].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(vararg rules: Rule<T>): RuleList<T> = RuleList(rules.asList())

/**
 * Combines `this` [RuleList] with another [Rule], producing a new [RuleList]
 * that executes all rules in sequence.
 *
 * If the [other] rule is itself a [RuleList], its rules are flattened into the resulting sequence.
 * The rules from `this` [RuleList] are executed first, followed by the rules from [other].
 */
public operator fun <T> RuleList<T>.plus(other: Rule<T>): RuleList<T> =
    when (other) {
        is RuleList<T> -> RuleList(rules + other.rules)
        else -> RuleList(rules + other)
    }

/**
 * Combines `this` [RuleList] with another [RuleList], producing a new [RuleList]
 * that executes all rules from both stacks in sequence.
 *
 * The rules from `this` [RuleList] are executed first, followed by the rules from [other].
 */
public operator fun <T> RuleList<T>.plus(other: RuleList<T>): RuleList<T> =
    RuleList(
        rules + other.rules,
    )
