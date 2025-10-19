package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

/**
 * Context that receives validation failures.
 *
 * Implementations decide how to handle reported [Violation]s (for example: aggregate, throw, or abort).
 */
public interface ValidationContext {
    /**
     * Called when a [violation] occurs.
     */
    public fun onFailure(violation: Violation)

    /**
     * Applies a single [rule] to `this` value within the current context and returns the value.
     */
    public infix fun <T> T.applyRule(rule: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@applyRule

        rule.run(
            context = context,
            value = value,
        )

        return value
    }

    /**
     * Applies each of the provided [rules] to `this` value within the current context and returns the value.
     *
     * Rules are executed in the order they are provided.
     */
    public fun <T> T.applyRules(vararg rules: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@applyRules

        for (rule in rules) {
            rule.run(
                context = context,
                value = value,
            )
        }

        return value
    }

    /**
     * Applies each of the provided [rules] to `this` value within the current context and returns the value.
     */
    public infix fun <T> T.applyRules(rules: Iterable<Rule<T>>): T {
        val context = this@ValidationContext
        val value = this@applyRules

        for (rule in rules) {
            rule.run(
                context = context,
                value = value,
            )
        }

        return value
    }
}

public inline fun ValidationContext(crossinline block: () -> Unit): ValidationContext =
    object : ValidationContext {
        override fun onFailure(violation: Violation): Unit = block()
    }

/**
 * Applies a single [rule] to `this` value within the provided [context] and returns the value.
 */
public fun <T> T.applyRuleUsing(
    context: ValidationContext,
    rule: Rule<T>,
): T {
    val value = this

    rule.run(
        context = context,
        value = value,
    )

    return value
}

/**
 * Applies [rules] to `this` value using the provided [context], returning the value.
 *
 * Rules are executed in the order they are provided.
 */
public fun <T> T.applyRulesUsing(
    context: ValidationContext,
    vararg rules: Rule<T>,
): T {
    val value = this

    for (rule in rules) {
        rule.run(
            context = context,
            value = value,
        )
    }

    return value
}

/**
 * Applies each of the provided [rules] to `this` value within the provided [context] and returns the value.
 */
public fun <T> T.applyRulesUsing(
    context: ValidationContext,
    rules: Iterable<Rule<T>>,
): T {
    val value = this

    for (rule in rules) {
        rule.run(
            context = context,
            value = value,
        )
    }

    return value
}

/**
 * Runs the given [rules] where the validated value is [Unit].
 */
public fun ValidationContext.runUnitRules(vararg rules: Rule<Unit>) {
    for (rule in rules) {
        rule.run(
            context = this,
            value = Unit,
        )
    }
}

/**
 * Reports a [Violation] if the given [condition] is `true`.
 */
public inline fun ValidationContext.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

/**
 * Reports a [Violation] if the given [condition] is `false`.
 */
public inline fun ValidationContext.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit =
    failIf(
        condition = !condition,
        lazyViolation = lazyViolation,
    )
