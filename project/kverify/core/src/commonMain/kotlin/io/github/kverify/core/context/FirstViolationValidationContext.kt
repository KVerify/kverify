@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface FirstViolationValidationContext : ValidationContext {
    public val firstViolation: Violation?

    override fun <T> T.verifyWith(rule: Rule<T>): T {
        val value = this
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        rule.execute(
            context = context,
            value = value,
        )

        return this
    }

    override fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        for (rule in rules) {
            rule.execute(
                context = context,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}

@PublishedApi
internal class FirstViolationValidationContextImpl : FirstViolationValidationContext {
    override var firstViolation: Violation? = null
        private set

    override fun onFailure(violation: Violation) {
        if (firstViolation == null) firstViolation = violation
    }
}

@PublishedApi
internal inline fun getFirstViolation(init: FirstViolationValidationContextImpl.() -> Unit): Violation? {
    contract {
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }

    return FirstViolationValidationContextImpl().apply(init).firstViolation
}

public inline fun verifyFirst(block: FirstViolationValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val firstViolation = getFirstViolation(block)

    return if (firstViolation == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(firstViolation)
    }
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyFirst(): ValidationResult.Valid = ValidationResult.Valid

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyFirst(rule: Rule<T>): ValidationResult {
    val value = this

    return verifyFirst { value verifyWith rule }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyFirst(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return verifyFirst { value.verifyWith(rules = rules) }
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyFirst(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return verifyFirst { value verifyWith rules }
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.passes(): Boolean = true

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passes(rule: Rule<T>): Boolean {
    val value = this

    return getFirstViolation { value verifyWith rule } == null
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.passes(vararg rules: Rule<T>): Boolean {
    val value = this

    return getFirstViolation { value.verifyWith(rules = rules) } == null
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passes(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return getFirstViolation { value verifyWith rules } == null
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notPasses(): Boolean = false

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPasses(rule: Rule<T>): Boolean =
    !passes(
        rule = rule,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.notPasses(vararg rules: Rule<T>): Boolean =
    !passes(
        rules = rules,
    )

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPasses(rules: Iterable<Rule<T>>): Boolean =
    !passes(
        rules = rules,
    )
