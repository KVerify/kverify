package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing
}

@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies !condition
    }

    if (condition) onFailure(lazyViolation())
}

@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies condition
    }

    this.failIf(!condition, lazyViolation)
}

public class ThrowingValidationContextImpl : ThrowingValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )
}

@PublishedApi
internal val ThrowingValidationObject: ThrowingValidationContext = ThrowingValidationContextImpl()

/**
 * Runs [block] in a [ThrowingValidationContext], returning the [block] result or throwing on first failure.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> validateOrThrow(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

/**
 * Validates `this` value with a single [rule] in a throwing context and returns the value.
 */
public infix fun <T> T.validateOrThrowWithRule(rule: Rule<T>): T {
    val value = this

    validateOrThrow { value applyRule rule }

    return value
}

/**
 * Validates `this` value with multiple [rules] in a throwing context and returns the value.
 */
public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): T {
    val value = this

    validateOrThrow { value.applyRules(rules = rules) }

    return value
}
