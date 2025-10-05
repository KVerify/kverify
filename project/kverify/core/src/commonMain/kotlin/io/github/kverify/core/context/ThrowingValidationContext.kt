package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public open class ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )

    @OptIn(ExperimentalContracts::class)
    public inline fun failIfNot(
        condition: Boolean,
        lazyViolation: () -> Violation,
    ) {
        contract {
            returns() implies condition
        }

        failIf(!condition, lazyViolation)
    }

    @OptIn(ExperimentalContracts::class)
    public inline fun failIf(
        condition: Boolean,
        lazyViolation: () -> Violation,
    ) {
        contract {
            returns() implies !condition
        }

        if (condition) onFailure(lazyViolation())
    }
}

@PublishedApi
internal val ThrowingValidationObject: ThrowingValidationContext = ThrowingValidationContext()

// ==========================
// Throwing validation
// ==========================
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

public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): T {
    val value = this

    validateOrThrow { value.applyRules(rules = rules) }

    return value
}

// ==========================
// Non-throwing validation
// ==========================
public fun validateFirst(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)

        ValidationResult.Valid
    } catch (exception: ThrowingValidationContextException) {
        ValidationResult(exception.violation)
    }

public infix fun <T> T.validateFirstWithRule(rule: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value applyRule rule }

    return result
}

public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult {
    val value = this

    val result = validateFirst { value.applyRules(rules = rules) }

    return result
}

public fun <T> runValidatingFirst(block: ThrowingValidationContext.() -> T): Result<T> =
    try {
        val result = ThrowingValidationObject.run(block)

        Result.success(result)
    } catch (exception: ThrowingValidationContextException) {
        Result.failure(exception)
    }
