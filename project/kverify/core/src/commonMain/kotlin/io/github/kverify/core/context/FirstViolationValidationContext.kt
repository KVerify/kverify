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

    override fun <T> T.applyRule(rule: Rule<T>): T {
        val value = this
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        rule.execute(
            context = context,
            value = value,
        )

        return this
    }

    override fun <T> runRules(
        value: T,
        rulesIterator: Iterator<Rule<T>>,
    ): T {
        if (firstViolation != null) return value

        for (rule in rulesIterator) {
            rule.execute(
                context = this,
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
internal inline fun runFirstViolation(init: FirstViolationValidationContextImpl.() -> Unit): Violation? {
    contract {
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }

    return FirstViolationValidationContextImpl().apply(init).firstViolation
}

public inline fun validateFirst(block: FirstViolationValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val firstViolation = runFirstViolation(block)

    return if (firstViolation == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(firstViolation)
    }
}

public inline fun <T> runValidatingFirst(block: FirstViolationValidationContext.() -> T): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val context = FirstViolationValidationContextImpl()

    val result = context.block()
    val firstViolation = context.firstViolation

    return if (firstViolation == null) {
        Result.success(result)
    } else {
        Result.failure(ThrowingValidationContextException(firstViolation))
    }
}

@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.validateFirst(): ValidationResult.Valid = ValidationResult.Valid

public infix fun <T> T.validateFirst(rule: Rule<T>): ValidationResult {
    val value = this

    return validateFirst { value applyRule rule }
}

public fun <T> T.validateFirst(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return validateFirst { value.applyRules(rules = rules) }
}

public infix fun <T> T.validateFirst(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return validateFirst { value applyRules rules }
}

public infix fun <T> T.validateFirst(rulesIterator: Iterator<Rule<T>>): ValidationResult {
    val value = this

    return validateFirst {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    }
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.satisfies(): Boolean = true

public infix fun <T> T.satisfies(rule: Rule<T>): Boolean {
    val value = this

    return runFirstViolation { value applyRule rule } == null
}

public fun <T> T.satisfies(vararg rules: Rule<T>): Boolean {
    val value = this

    return runFirstViolation { value.applyRules(rules = rules) } == null
}

public infix fun <T> T.satisfies(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return runFirstViolation { value applyRules rules } == null
}

public fun <T> T.satisfies(rulesIterator: Iterator<Rule<T>>): Boolean {
    val value = this

    return runFirstViolation {
        runRules(
            value = value,
            rulesIterator = rulesIterator,
        )
    } == null
}

@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notSatisfies(): Boolean = false

public infix fun <T> T.notSatisfies(rule: Rule<T>): Boolean =
    !satisfies(
        rule = rule,
    )

public fun <T> T.notSatisfies(vararg rules: Rule<T>): Boolean =
    !satisfies(
        rules = rules,
    )

public infix fun <T> T.notSatisfies(rules: Iterable<Rule<T>>): Boolean =
    !satisfies(
        rules = rules,
    )

public fun <T> T.notSatisfies(rulesIterator: Iterator<Rule<T>>): Boolean =
    !satisfies(
        rulesIterator = rulesIterator,
    )
