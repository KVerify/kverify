package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationContext] implementation that immediately throws a [ValidationException] on the first validation failure.
 *
 * This context provides fail-fast validation behavior, stopping execution as soon as any validation rule fails.
 * It's ideal for scenarios where immediate error handling is required and collecting multiple violations is unnecessary.
 *
 * The context throws [ValidationException] containing the first encountered [Violation], making it suitable
 * for input validation, precondition checks, and other scenarios where early termination is desired.
 */
public class ThrowingValidationContext : ValidationContext {
    override fun onFailure(violation: Violation): Nothing =
        throw ValidationException(
            listOf(violation),
        )

    /**
     * Validates a condition and throws on failure.
     *
     * If the [condition] is `false`, executes the [lazyViolation] to create a [Violation]
     * and immediately throws a [ValidationException].
     *
     * The Kotlin [contract] ensures that after this function returns normally,
     * the condition is guaranteed to be `true`.
     *
     * @param condition The boolean condition to validate.
     * @param lazyViolation A lambda that produces the [Violation] when validation fails.
     * @throws ValidationException When the condition is `false`.
     */
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

/**
 * A singleton instance of [ThrowingValidationContext] for easy access.
 * Hides [ThrowingValidationContext.validate] from IDE import suggestions.
 */
@PublishedApi
internal val ThrowingValidationObject: ThrowingValidationContext = ThrowingValidationContext()

/**
 * Validates a condition and throws on failure.
 *
 * If the [condition] is `false`, executes the [violationFactory] to create a [Violation]
 * and immediately throws a [ValidationException].
 *
 * The Kotlin [contract] ensures that after this function returns normally,
 * the condition is guaranteed to be `true`.
 *
 * Internally uses default singleton implementation of [ThrowingValidationContext].
 *
 * @param condition The boolean condition to validate.
 * @param violationFactory A lambda that produces the [Violation] when validation fails.
 * @throws ValidationException When the condition is `false`.
 */
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

/**
 * Runs a [block] of validation logic with fail-fast behavior.
 *
 * Internally uses [ThrowingValidationContext] to execute the validation [block].
 * Execution stops immediately upon the first validation failure, throwing a [ValidationException].
 *
 * Best suited for input validation, precondition checks, and scenarios where
 * early termination on failure is desired.
 *
 * @param block A lambda with validation logic to execute.
 * @throws ValidationException When any validation within the block fails.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateOrThrow(block: ThrowingValidationContext.() -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    ThrowingValidationObject.apply(block)
}

/**
 * Runs a [block] of validation logic and captures the first failure as a [ValidationResult].
 *
 * Internally uses [ThrowingValidationContext] but catches any [ValidationException]
 * to return a [ValidationResult] instead of propagating the exception.
 *
 * Returns [ValidationResult.Valid] if no violations occur, or a [ValidationResult]
 * containing the first encountered violation.
 *
 * Best suited for validation scenarios where you want fail-fast behavior
 * but prefer result objects over exception handling.
 *
 * @param block A lambda with validation logic to execute.
 * @return A [ValidationResult] representing the validation outcome.
 */
public inline fun validateFirst(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    try {
        validateOrThrow(block)
        ValidationResult.Valid
    } catch (violation: ValidationException) {
        ValidationResult(violation.violations)
    }

/**
 * Applies the given [rules] to the receiver [T] with fail-fast behavior.
 *
 * Internally uses [ThrowingValidationContext] to apply rules sequentially.
 * Execution stops immediately upon the first rule violation, throwing a [ValidationException].
 *
 * Best suited for validating a single value with multiple rules
 * when immediate failure response is required.
 *
 * @param rules The rules to apply to the receiver object.
 * @throws ValidationException When any rule violation occurs.
 */
public fun <T> T.validateOrThrowWithRules(vararg rules: Rule<T>): Unit =
    validateOrThrow {
        this@validateOrThrowWithRules.applyRules(rules = rules)
    }

/**
 * Applies the given [rules] to the receiver [T] and captures the first failure as a [ValidationResult].
 *
 * Internally uses [ThrowingValidationContext] to apply rules but catches any [ValidationException]
 * to return a [ValidationResult] instead of propagating the exception.
 *
 * Returns [ValidationResult.Valid] if all rules pass, or a [ValidationResult]
 * containing the first rule violation encountered.
 *
 * Best suited for validating a single value with multiple rules
 * when you want fail-fast behavior but prefer result objects over exception handling.
 *
 * @param rules The rules to apply to the receiver object.
 * @return A [ValidationResult] containing the first violation or indicating success.
 */
public fun <T> T.validateFirstWithRules(vararg rules: Rule<T>): ValidationResult =
    validateFirst {
        this@validateFirstWithRules.applyRules(rules = rules)
    }

/**
 * Executes a [block] that may perform validation, returning the result or a failure.
 *
 * If no [Violation]s occur, returns [Result.success] with the [block]'s return value.
 * Otherwise, returns [Result.failure] wrapping the [ValidationException] from the first violation.
 *
 * Internally uses [ThrowingValidationContext] to provide fail-fast validation behavior
 * while wrapping the outcome in a [Result] for functional error handling.
 *
 * @param block A lambda with validation and business logic to execute.
 * @return A [Result] containing either the [block]'s result or a [ValidationException].
 */
public inline fun <T> runValidatingFirst(block: ThrowingValidationContext.() -> T): Result<T> =
    try {
        val result = ThrowingValidationObject.run(block)
        Result.success(result)
    } catch (e: ValidationException) {
        Result.failure(e)
    }
