package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

/**
 * A unit of validation logic.
 *
 * @see RuleList
 * @see io.github.kverify.core.rule.predicate.PredicateRule
 */
public interface Rule<in T> {
    /**
     * Defines the validation logic for the given [value] using the [context].
     */
    public fun execute(
        context: ValidationContext,
        value: T,
    )
}

/**
 * @return a new anonymous object that implements [Rule] interface with given [execute] lambda.
 */
public inline fun <T> Rule(crossinline execute: ValidationContext.(T) -> Unit): Rule<T> =
    object : Rule<T> {
        override fun execute(
            context: ValidationContext,
            value: T,
        ): Unit = execute(context, value)
    }

/**
 * Combines `this` rule with another rule for sequential execution.
 *
 * If either rule is a [RuleList], its [rules][RuleList.rules] are flattened into the result.
 * Otherwise, both rules are combined into a new [RuleList].
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
 * @param other The [Rule] to combine with
 * @return A new [RuleList] containing all rules
 */
public operator fun <T> Rule<T>.plus(other: Rule<T>): Rule<T> =
    when {
        this is RuleList && other is RuleList -> RuleList(this.rules + other.rules)
        this is RuleList -> RuleList(this.rules + other)
        else -> RuleList(this, other)
    }
