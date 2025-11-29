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
public inline fun <T> validateOrThrow(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

public infix fun <T> T.validateOrThrow(rule: Rule<T>): T {
    val value = this

    return validateOrThrow { value applyRule rule }
}

public infix fun <T> T.validateOrThrow(rulesIterator: Iterator<Rule<T>>): T {
    val value = this

    return validateOrThrow {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public infix fun <T> T.validateOrThrow(rules: Iterable<Rule<T>>): T {
    val value = this

    return validateOrThrow { value.applyRules(rules = rules) }
}

public fun <T> T.validateOrThrow(vararg rules: Rule<T>): T {
    val value = this

    return validateOrThrow { value.applyRules(rules = rules) }
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

public infix fun <T> T.validateFailFast(rule: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value applyRule rule }
}

public infix fun <T> T.validateFailFast(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return validateFailFast { value.applyRules(rules = rules) }
}

public fun <T> T.validateFailFast(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return validateFailFast { value.applyRules(rules = rules) }
}

public infix fun <T> T.validateFailFast(rulesIterator: Iterator<Rule<T>>): ValidationResult {
    val value = this

    return validateFailFast {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
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

public infix fun <T> T.satisfiesFailFast(rule: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value applyRule rule }
}

public infix fun <T> T.satisfiesFailFast(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows { value applyRules rules }
}

public fun <T> T.satisfiesFailFast(vararg rules: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value.applyRules(rules = rules) }
}

public fun <T> T.satisfiesFailFast(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

public infix fun <T> T.notSatisfiesFailFast(rule: Rule<T>): Boolean =
    !satisfiesFailFast(
        rule = rule,
    )

public infix fun <T> T.notSatisfiesFailFast(rules: Iterable<Rule<T>>): Boolean =
    !satisfiesFailFast(
        rules = rules,
    )

public fun <T> T.notSatisfiesFailFast(vararg rules: Rule<T>): Boolean =
    !satisfiesFailFast(
        rules = rules,
    )

public fun <T> T.notSatisfiesFailFast(rulesIterator: Iterator<Rule<T>>): Boolean =
    !satisfiesFailFast(
        rulesIterator = rulesIterator,
    )
