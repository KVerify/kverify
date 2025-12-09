package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext

/**
 * A unit of validation logic.
 *
 * @see RuleList
 * @see ScopedRule
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
 * Combines `this` rule with another rule for sequential execution.
 *
 * If either rule is a [RuleList], its rules are flattened into the result.
 * Otherwise, both rules are combined into a new [RuleList].
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
