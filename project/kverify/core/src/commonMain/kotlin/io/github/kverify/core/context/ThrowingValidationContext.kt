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
public inline fun <T> verifyThrowing(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyThrowing(): T = this

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyThrowing(rule: Rule<T>): T {
    val value = this

    return verifyThrowing { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyThrowing(vararg rules: Rule<T>): T {
    val value = this

    return verifyThrowing { value.verifyWith(rules = rules) }
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyThrowing(rules: Iterable<Rule<T>>): T {
    val value = this

    return verifyThrowing { value.verifyWith(rules = rules) }
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

public inline fun verifyFailFast(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    runFailFast({
        ThrowingValidationObject.block()
        ValidationResult.Valid
    }) {
        ValidationResult.Invalid(it.violation)
    }

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyFailFast(): ValidationResult = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyFailFast(rule: Rule<T>): ValidationResult {
    val value = this

    return verifyFailFast { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyFailFast(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return verifyFailFast { value.verifyWith(rules = rules) }
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyFailFast(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return verifyFailFast { value.verifyWith(rules = rules) }
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
public inline fun <T> T.passesFailFast(): Boolean = true

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passesFailFast(rule: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.passesFailFast(vararg rules: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value.verifyWith(rules = rules) }
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passesFailFast(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rules }
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notPassesFailFast(): Boolean = false

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPassesFailFast(rule: Rule<T>): Boolean =
    !passesFailFast(
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.notPassesFailFast(vararg rules: Rule<T>): Boolean =
    !passesFailFast(
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPassesFailFast(rules: Iterable<Rule<T>>): Boolean =
    !passesFailFast(
        rules = rules,
    )
