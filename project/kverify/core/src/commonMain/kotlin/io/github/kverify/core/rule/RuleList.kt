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
 * @return empty [RuleList].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(): RuleList<T> = RuleList(emptyList())

/**
 * @return [RuleList] containing a single [rule].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(rule: Rule<T>): RuleList<T> = RuleList(listOf(rule))

/**
 * @return [RuleList] containing the given [rules].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> RuleList(vararg rules: Rule<T>): RuleList<T> = RuleList(rules.asList())

/**
 * Combines `this` rule list with another rule.
 *
 * If [other] is a [RuleList], its [rules][RuleList.rules] are flattened into the result.
 * Otherwise, [other] is appended as a single rule.
 *
 * ### Example:
 * ```kt
 * val ruleList1 = RuleList(rule1, rule2)
 * val ruleList2 = RuleList(rule3, rule4)
 *
 * // RuleList(rule1, rule2, rule3)
 * ruleList1 + rule3
 *
 * // RuleList(rule1, rule2, rule3, rule4)
 * ruleList1 + ruleList2
 * ```
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
 * The [rules][RuleList.rules] from both lists are flattened into a single list.
 *
 * ### Example:
 * ```kt
 * val ruleList1 = RuleList(rule1, rule2)
 * val ruleList2 = RuleList(rule3, rule4)
 *
 * // RuleList(rule1, rule2, rule3, rule4)
 * ruleList1 + ruleList2
 * ```
 *
 * @param other The [RuleList] to add
 * @return A new [RuleList] containing all rules from both lists
 */
public operator fun <T> RuleList<T>.plus(other: RuleList<T>): RuleList<T> =
    RuleList(
        rules + other.rules,
    )
