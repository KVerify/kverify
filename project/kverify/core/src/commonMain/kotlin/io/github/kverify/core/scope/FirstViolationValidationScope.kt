package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface FirstViolationValidationScope : ValidationScope {
    public val firstViolation: Violation?

    override fun <T> T.verifyWith(rule: Rule<T>): T {
        val value = this
        val scope = this@FirstViolationValidationScope

        if (firstViolation != null) return value

        rule.execute(
            scope = scope,
            value = value,
        )

        return this
    }

    override fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val scope = this@FirstViolationValidationScope

        if (firstViolation != null) return value

        for (rule in rules) {
            rule.execute(
                scope = scope,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}

public class DefaultFirstViolationValidationScope(
    override val validationContext: ValidationContext = EmptyValidationContext,
) : FirstViolationValidationScope {
    override var firstViolation: Violation? = null
        private set

    override fun onFailure(violation: Violation) {
        if (firstViolation == null) {
            firstViolation = violation
        }
    }
}

public inline fun verifyWithFirstViolation(
    validationContext: ValidationContext = EmptyValidationContext,
    crossinline block: FirstViolationValidationScope.() -> Unit,
): Violation? {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return DefaultFirstViolationValidationScope(validationContext)
        .apply(block)
        .firstViolation
}

public infix fun <T> T.passes(rule: Rule<T>): Boolean {
    val value = this

    return verifyWithFirstViolation {
        value verifyWith rule
    } == null
}

public infix fun <T> T.passes(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return verifyWithFirstViolation {
        value verifyWith rules
    } == null
}

public fun <T> T.passes(vararg rules: Rule<T>): Boolean {
    val value = this

    return verifyWithFirstViolation {
        value.verifyWith(rules = rules)
    } == null
}

public infix fun <T> T.notPasses(rule: Rule<T>): Boolean = !passes(rule)

public infix fun <T> T.notPasses(rules: Iterable<Rule<T>>): Boolean = !passes(rules)

public fun <T> T.notPasses(vararg rules: Rule<T>): Boolean = !passes(rules = rules)
