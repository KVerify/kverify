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
    override fun ValidationContext.runValidation(value: T) {
        for (rule in rules) {
            rule.runValidation(
                context = this@runValidation,
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
