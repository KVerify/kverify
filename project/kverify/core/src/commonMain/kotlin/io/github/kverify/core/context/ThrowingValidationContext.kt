@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.js.JsName

public fun interface ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing
}

@Suppress("NOTHING_TO_INLINE")
@JsName("ThrowingValidationContextFactory")
public inline fun ThrowingValidationContext(): ThrowingValidationContext = ThrowingValidationObject

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

    failIf(!condition, lazyViolation)
}

@PublishedApi
internal object ThrowingValidationObject : ThrowingValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )
}

// ============================================================
// Throw on failure
// ============================================================
@OptIn(ExperimentalContracts::class)
public inline fun <T> validateThrowing(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.validateThrowing(): T = this

public infix fun <T> T.validateThrowing(rule: Rule<T>): T {
    val value = this

    return validateThrowing { value verifyWith rule }
}

public fun <T> T.validateThrowing(vararg rules: Rule<T>): T {
    val value = this

    return validateThrowing { value.verifyWith(rules = rules) }
}

public infix fun <T> T.validateThrowing(rules: Iterable<Rule<T>>): T {
    val value = this

    return validateThrowing { value.verifyWith(rules = rules) }
}

// ============================================================
// Throw and catch on failure
// ============================================================
@PublishedApi
internal inline fun <T> runFailFast(
    block: ThrowingValidationContext.() -> T,
    onFailure: (ThrowingValidationContextException) -> T,
): T =
    try {
        block(ThrowingValidationObject)
    } catch (e: ThrowingValidationContextException) {
        onFailure(e)
    }

public inline fun validateFailFast(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    runFailFast({
        ThrowingValidationObject.block()
        ValidationResult.Valid
    }) {
        ValidationResult.Invalid(it.violation)
    }

public inline fun <T> runValidatingFailFast(block: ThrowingValidationContext.() -> T): Result<T> =
    runFailFast({
        Result.success(block())
    }) {
        Result.failure(it)
    }

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.validateFailFast(): ValidationResult = ValidationResult.Valid

public infix fun <T> T.validateFailFast(rule: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value verifyWith rule }
}

public fun <T> T.validateFailFast(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value.verifyWith(rules = rules) }
}

public infix fun <T> T.validateFailFast(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return validateFailFast { value.verifyWith(rules = rules) }
}

// ============================================================
// Satisfies
// ============================================================
@PublishedApi
internal inline fun failFastThrows(block: ThrowingValidationContext.() -> Unit): Boolean =
    runFailFast({
        ThrowingValidationObject.block()
        false
    }) {
        true
    }

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.satisfiesFailFast(): Boolean = true

public infix fun <T> T.satisfiesFailFast(rule: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rule }
}

public fun <T> T.satisfiesFailFast(vararg rules: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value.verifyWith(rules = rules) }
}

public infix fun <T> T.satisfiesFailFast(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rules }
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notSatisfiesFailFast(): Boolean = true

public infix fun <T> T.notSatisfiesFailFast(rule: Rule<T>): Boolean =
    !satisfiesFailFast(
        rule = rule,
    )

public fun <T> T.notSatisfiesFailFast(vararg rules: Rule<T>): Boolean =
    !satisfiesFailFast(
        rules = rules,
    )

public infix fun <T> T.notSatisfiesFailFast(rules: Iterable<Rule<T>>): Boolean =
    !satisfiesFailFast(
        rules = rules,
    )
