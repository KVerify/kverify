package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Implementation of [ValidationContext] that throws a [ValidationException]
 * containing the first [Violation] reported via [ValidationContext.onFailure].
 */
public open class ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing = throw ValidationException(listOf(violation))

    /**
     * Converts [message] into [io.github.kverify.core.violation.StringViolation]
     * and handles a validation failure.
     */
    public fun onFailure(message: String): Nothing = onFailure(message.asViolation())

    /**
     * Uses Kotlin contracts to indicate that a successful return implies [condition] was `true`.
     *
     * @throws ValidationException with the result of calling [violationGenerator]
     * if [condition] is `false`.
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun validate(
        condition: Boolean,
        violationGenerator: () -> Violation,
    ) {
        contract {
            returns() implies condition
        }

        if (!condition) onFailure(violationGenerator())
    }

    public companion object : ThrowingValidationContext()
}

/**
 * Uses Kotlin contracts to indicate that a successful return implies [condition] was true.
 *
 * @throws ValidationException with the result of calling [violationGenerator]
 * if [condition] is `false`.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateThatOrThrow(
    condition: Boolean,
    violationGenerator: () -> Violation,
) {
    contract {
        returns() implies condition
    }

    validateOrThrow {
        validate(
            condition,
            violationGenerator,
        )
    }
}

/**
 * Executes the given [block] within a [ThrowingValidationContext] context.
 *
 * @throws ValidationException if any [Violation] is reported via [ValidationContext.onFailure].
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateOrThrow(block: ThrowingValidationContext.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    ThrowingValidationContext.apply(block)
}

/**
 * Executes the given [block] within a [ThrowingValidationContext] context.
 *
 * @return [ValidationResult] containing the first [Violation] reported via [ValidationContext.onFailure],
 * or [ValidationResult.VALID] if no violations occurred.
 */
public inline fun validateFirst(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.VALID
    } catch (violation: ValidationException) {
        ValidationResult(violation.violations)
    }

/**
 * Applies the given [rules] to this value within a [ThrowingValidationContext] context.
 *
 * @throws ValidationException if any [Violation] is reported via [ValidationContext.onFailure].
 */
public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        applyRules(rules = rules)
    }

/**
 * Applies the given [rules] to this value within a [ThrowingValidationContext] context.
 *
 * @return [ValidationResult] containing the first [Violation] reported via [ValidationContext.onFailure],
 * or [ValidationResult.VALID] if no violations occurred.
 */
public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        applyRules(rules = rules)
    }

/**
 * Runs [block] within a [ThrowingValidationContext] context,
 * stopping if any [Violation] is reported via [ValidationContext.onFailure].
 *
 * @return [Result.success], wrapping result of running [block] if no [Violation]s were reported.
 * [Result.failure], wrapping [ValidationException] with the first reported [Violation] otherwise.
 */
public inline fun <T> runValidatingFirst(block: ThrowingValidationContext.() -> T): Result<T> =
    try {
        Result.success(
            ThrowingValidationContext.run(block),
        )
    } catch (e: ValidationException) {
        Result.failure(e)
    }
