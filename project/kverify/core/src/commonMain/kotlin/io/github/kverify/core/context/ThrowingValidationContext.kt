package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )

    @OptIn(ExperimentalContracts::class)
    public inline fun validate(
        condition: Boolean,
        lazyViolation: () -> Violation,
    ) {
        contract {
            returns() implies condition
        }

        if (!condition) onFailure(lazyViolation())
    }
}

@PublishedApi
internal val ThrowingValidationObject: ThrowingValidationContext = ThrowingValidationContext()

@OptIn(ExperimentalContracts::class)
public inline fun validateThatOrThrow(
    condition: Boolean,
    violationFactory: () -> Violation,
) {
    contract {
        returns() implies condition
    }

    ThrowingValidationObject.validate(
        condition,
        violationFactory,
    )
}

@OptIn(ExperimentalContracts::class)
public inline fun validateOrThrow(block: ThrowingValidationContext.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    ThrowingValidationObject.apply(block)
}

public inline fun validateFirst(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.Valid
    } catch (exception: ThrowingValidationContextException) {
        ValidationResult(exception.violation)
    }

public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        this@validateOrThrowWithRules.applyRules(rules = rules)
    }

public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        this@validateFirstWithRules.applyRules(rules = rules)
    }

public inline fun <T> runValidatingFirst(block: ThrowingValidationContext.() -> T): Result<T> =
    try {
        val result = ThrowingValidationObject.run(block)
        Result.success(result)
    } catch (exception: ThrowingValidationContextException) {
        Result.failure(exception)
    }
