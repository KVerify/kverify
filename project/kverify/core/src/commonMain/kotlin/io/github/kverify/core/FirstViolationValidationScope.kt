package io.github.kverify.core

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface FirstViolationValidationScope : ValidationScope {
    public val firstViolation: Violation?
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
        if (firstViolation != null) return@verifyWithFirstViolation

        rule.execute(this, value)
    } == null
}

public infix fun <T> T.passes(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return verifyWithFirstViolation {
        if (firstViolation != null) return@verifyWithFirstViolation

        for (rule in rules) {
            rule.execute(this, value)
            if (firstViolation != null) break
        }
    } == null
}

public fun <T> T.passes(vararg rules: Rule<T>): Boolean = passes(rules.asList())

public infix fun <T> T.notPasses(rule: Rule<T>): Boolean = !passes(rule)

public infix fun <T> T.notPasses(rules: Iterable<Rule<T>>): Boolean = !passes(rules)

public fun <T> T.notPasses(vararg rules: Rule<T>): Boolean = !passes(rules = rules)
