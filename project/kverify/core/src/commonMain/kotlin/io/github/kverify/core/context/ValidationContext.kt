package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

public interface ValidationContext {
    public fun onFailure(violation: Violation)

    public infix fun <T> T.applyRule(rule: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@applyRule

        rule.run(
            context = context,
            value = value,
        )

        return value
    }

    public fun <T> T.applyRules(vararg rules: Rule<T>): T =
        applyRulesInternal(
            value = this@applyRules,
            rulesIterator = rules.iterator(),
        )

    public infix fun <T> T.applyRules(rules: Iterable<Rule<T>>): T =
        applyRulesInternal(
            value = this@applyRules,
            rulesIterator = rules.iterator(),
        )

    private fun <T> applyRulesInternal(
        value: T,
        rulesIterator: Iterator<Rule<T>>,
    ): T {
        val context = this@ValidationContext

        for (rule in rulesIterator) {
            rule.run(
                context = context,
                value = value,
            )
        }

        return value
    }
}

public inline fun ValidationContext(crossinline block: (Violation) -> Unit): ValidationContext =
    object : ValidationContext {
        override fun onFailure(violation: Violation): Unit = block(violation)
    }

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

public fun ValidationContext.runUnitRules(vararg rules: Rule<Unit>) {
    for (rule in rules) {
        rule.run(
            context = this,
            value = Unit,
        )
    }
}

public inline fun ValidationContext.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

public inline fun ValidationContext.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit =
    failIf(
        condition = !condition,
        lazyViolation = lazyViolation,
    )
