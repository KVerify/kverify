package io.github.kverify.core.context

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

public fun interface ValidationContext {
    public fun onFailure(violation: Violation)

    public infix fun <T> T.verifyWith(rule: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@verifyWith

        rule.execute(
            context = context,
            value = value,
        )

        return value
    }

    public infix fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val context = this@ValidationContext

        for (rule in rules) {
            rule.execute(
                context = context,
                value = value,
            )
        }

        return value
    }

    public fun <T> T.verifyWith(vararg rules: Rule<T>): T =
        this.verifyWith(
            rules = rules.asIterable(),
        )
}

@Suppress("UnusedReceiverParameter")
public fun <T> ValidationContext.verify(value: T): T = value

public fun <T> ValidationContext.verify(
    value: T,
    rule: Rule<T>,
): T = value verifyWith rule

public fun <T> ValidationContext.verify(
    value: T,
    vararg rules: Rule<T>,
): T = value.verifyWith(rules = rules)

public fun <T> ValidationContext.verify(
    value: T,
    rules: Iterable<Rule<T>>,
): T = value verifyWith rules

@Suppress("UnusedParameter")
public fun <T> T.verifyWith(context: ValidationContext): T = this

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
