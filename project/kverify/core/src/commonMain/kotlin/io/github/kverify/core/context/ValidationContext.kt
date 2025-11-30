package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

public fun interface ValidationContext {
    public fun onFailure(violation: Violation)

    public fun <T> verify(
        value: T,
        rulesIterator: Iterator<Rule<T>>,
    ): T {
        val context = this@ValidationContext

        for (rule in rulesIterator) {
            rule.execute(
                context = context,
                value = value,
            )
        }

        return value
    }

    public infix fun <T> T.verifyWith(rule: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@verifyWith

        rule.execute(
            context = context,
            value = value,
        )

        return value
    }

    public fun <T> T.verifyWith(vararg rules: Rule<T>): T =
        verify(
            value = this@verifyWith,
            rulesIterator = rules.iterator(),
        )

    public infix fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T =
        verify(
            value = this@verifyWith,
            rulesIterator = rules.iterator(),
        )
}

public fun <T> T.verifyWith(
    context: ValidationContext,
    rule: Rule<T>,
): T {
    val value = this

    return with(context) { value verifyWith rule }
}

public fun <T> T.verifyWith(
    context: ValidationContext,
    vararg rules: Rule<T>,
): T {
    val value = this

    return with(context) { value.verifyWith(rules = rules) }
}

public fun <T> T.verifyWith(
    context: ValidationContext,
    rules: Iterable<Rule<T>>,
): T {
    val value = this

    return with(context) { value.verifyWith(rules = rules) }
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
