package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import kotlin.jvm.JvmInline

/**
 * A composite [Rule] that executes multiple [rules] sequentially.
 */
@JvmInline
public value class RuleList<in T>(
    public val rules: List<Rule<T>>,
) : Rule<T> {
    /**
     * Executes all [rules] sequentially with the given [context] and [value].
     */
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
 * Creates a [RuleList] containing the given [rules].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(vararg rules: Rule<T>): RuleList<T> = RuleList(rules.asList())

/**
 * Combines `this` rule list with another rule.
 *
 * If [other] is a [RuleList], its rules are flattened into the result.
 * Otherwise, [other] is appended as a single rule.
 *
 * @param other The [Rule] to add
 * @return A new rule list containing all rules
 */
public operator fun <T> RuleList<T>.plus(other: Rule<T>): RuleList<T> =
    when (other) {
        is RuleList<T> -> RuleList(rules + other.rules)
        else -> RuleList(rules + other)
    }

/**
 * Combines `this` rule list with another rule list.
 *
 * The rules from both lists are flattened into a single list.
 *
 * @param other The [RuleList] to add
 * @return A new [RuleList] containing all rules from both lists
 */
public operator fun <T> RuleList<T>.plus(other: RuleList<T>): RuleList<T> =
    RuleList(
        rules + other.rules,
    )
