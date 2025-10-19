package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

/**
 * Composite [Rule] that executes a list of inner [rules] sequentially.
 */
@JvmInline
public value class StackedRule<in T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    override fun run(
        context: ValidationContext,
        value: T,
    ) {
        for (rule in rules) {
            rule.run(
                context = context,
                value = value,
            )
        }
    }
}

/**
 * Creates an empty [StackedRule].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedRule(): StackedRule<T> = StackedRule(emptyList())

/**
 * Creates a [StackedRule] containing a single [rule].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedRule(rule: Rule<T>): StackedRule<T> = StackedRule(listOf(rule))

/**
 * Creates a [StackedRule] from the provided [rules].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedRule(vararg rules: Rule<T>): StackedRule<T> = StackedRule(rules.asList())

/**
 * Combines `this` [StackedRule] with another [Rule], producing a new [StackedRule]
 * that executes all rules in sequence.
 *
 * If the [other] rule is itself a [StackedRule], its rules are flattened into the resulting sequence.
 * The rules from `this` [StackedRule] are executed first, followed by the rules from [other].
 */
public operator fun <T> StackedRule<T>.plus(other: Rule<T>): StackedRule<T> =
    when (other) {
        is StackedRule<T> -> StackedRule(rules + other.rules)
        else -> StackedRule(rules + other)
    }

/**
 * Combines `this` [StackedRule] with another [StackedRule], producing a new [StackedRule]
 * that executes all rules from both stacks in sequence.
 *
 * The rules from `this` [StackedRule] are executed first, followed by the rules from [other].
 */
public operator fun <T> StackedRule<T>.plus(other: StackedRule<T>): StackedRule<T> =
    StackedRule(
        rules + other.rules,
    )
