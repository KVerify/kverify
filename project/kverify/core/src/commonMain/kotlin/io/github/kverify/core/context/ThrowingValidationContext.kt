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

public inline fun ThrowingValidationContext(crossinline onFailure: (Violation) -> Nothing): ThrowingValidationContext =
    object : ThrowingValidationContext {
        override fun onFailure(violation: Violation): Nothing = onFailure(violation)
    }

@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.throwingFailIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies !condition
    }

    if (condition) onFailure(lazyViolation())
}

@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.throwingFailIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies condition
    }

    this.failIf(!condition, lazyViolation)
}

@PublishedApi
internal object ThrowingValidationObject : ThrowingValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )
}

@OptIn(ExperimentalContracts::class)
public inline fun <T> validateOrThrow(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

public infix fun <T> T.validateOrThrowWithRule(rule: Rule<T>): T {
    val value = this

    validateOrThrow { value applyRule rule }

    return value
}

public infix fun <T> T.validateOrThrowWithRules(rulesIterator: Iterator<Rule<T>>): T {
    val value = this

    validateOrThrow {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }

    return value
}

public infix fun <T> T.validateOrThrowWithRules(rules: Iterable<Rule<T>>): T {
    val value = this

    validateOrThrow { value.applyRules(rules = rules) }

    return value
}

public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): T {
    val value = this

    validateOrThrow { value.applyRules(rules = rules) }

    return value
}
