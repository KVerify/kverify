package io.github.kverify.core.validator

import io.github.kverify.core.context.ValidationContext
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
class ThrowingValidator : ValidationContext {
    override fun onFailure(violation: Violation): Nothing = throw ValidationException(listOf(violation))

    /**
     * Converts [message] into [io.github.kverify.core.violation.StringViolation]
     * and handles a validation failure.
     */
    fun onFailure(message: String): Nothing = onFailure(message.asViolation())

    /**
     * Uses Kotlin contracts to indicate that a successful return implies [condition] was true.
     *
     * @throws ValidationException with the result of calling [violationGenerator]
     * if [condition] is `false`.
     */
    @OptIn(ExperimentalContracts::class)
    inline fun validate(
        condition: Boolean,
        violationGenerator: () -> Violation,
    ) {
        contract {
            returns() implies condition
        }

        if (!condition) onFailure(violationGenerator())
    }
}

/**
 * Executes the given [block] within a [ThrowingValidator] context.
 *
 * @throws ValidationException if any [Violation] is reported via [ValidationContext.onFailure].
 */
@OptIn(ExperimentalContracts::class)
inline fun validateOrThrow(block: ThrowingValidator.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    ThrowingValidator().apply(block)
}

/**
 * Uses Kotlin contracts to indicate that a successful return implies [condition] was true.
 *
 * @throws ValidationException with the result of calling [violationGenerator]
 * if [condition] is `false`.
 */
@OptIn(ExperimentalContracts::class)
inline fun validateOrThrow(
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
 * Executes the given [block] within a [ThrowingValidator] context.
 *
 * @return [ValidationResult] containing the first [Violation] reported via [ValidationContext.onFailure],
 * or [ValidationResult.VALID] if no violations occurred.
 */
inline fun validateFirst(block: ThrowingValidator.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.VALID
    } catch (violation: ValidationException) {
        ValidationResult(violation.violations)
    }

/**
 * Applies the given [rules] to this value within a [ThrowingValidator] context.
 *
 * @throws ValidationException if any [Violation] is reported via [ValidationContext.onFailure].
 */
fun <T> T.validateOrThrow(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        applyRules(*rules)
    }

/**
 * Applies the given [rules] to this value within a [ThrowingValidator] context.
 *
 * @return [ValidationResult] containing the first [Violation] reported via [ValidationContext.onFailure],
 * or [ValidationResult.VALID] if no violations occurred.
 */
fun <T> T.validateFirst(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        applyRules(*rules)
    }

/**
 * Runs [block] within a [ThrowingValidator] context,
 * stopping if any [Violation] is reported via [ValidationContext.onFailure].
 *
 * @return [Result.success], wrapping result of running [block] if no [Violation]s were reported.
 * [Result.failure], wrapping [ValidationException] with the first reported [Violation] otherwise.
 */
inline fun <T> runValidatingFirst(block: ThrowingValidator.() -> T): Result<T> =
    try {
        Result.success(
            ThrowingValidator().run(block),
        )
    } catch (e: ValidationException) {
        Result.failure(e)
    }
