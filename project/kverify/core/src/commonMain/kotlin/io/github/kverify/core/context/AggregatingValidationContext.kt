package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.model.fold
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationContext] implementation that accumulates all validation [Violation]s.
 *
 * This context allows multiple validation failures to be collected instead of stopping at the first one.
 * It stores violations in a provided mutable collection, enabling custom or shared storage logic.
 *
 * @property violationsStorage The mutable collection where [Violation]s will be stored.
 */
public open class AggregatingValidationContext(
    public val violationsStorage: MutableCollection<Violation>,
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }

    /**
     * Finalizes and builds a [ValidationResult] containing all accumulated [Violation]s.
     *
     * A copy of the current [violationsStorage] is created to ensure immutability and thread safety.
     *
     * @return A [ValidationResult] representing the collected validation state.
     */
    public open fun build(): ValidationResult =
        ValidationResult(
            violationsStorage.toList(),
        )
}

// ----Validate using AggregatingValidationContext with ArrayList as violationStorage----

/**
 * Runs a [block] of validation logic
 * and collects all [Violation]s using a fresh [AggregatingValidationContext].
 *
 * Internally creates a new [AggregatingValidationContext]
 * with a new [ArrayList] as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * Best suited for validating multiple values or complex validation flows
 * when no custom storage/context is required.
 *
 * @param block A lambda with validation logic to execute.
 * @return A [ValidationResult] containing all collected [Violation]s.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateAll(block: AggregatingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllTo(ArrayList(), block)
}

/**
 * Applies the given [rules] to the receiver [T]
 * and collects all [Violation]s using a fresh [AggregatingValidationContext].
 *
 * Internally creates a new [AggregatingValidationContext]
 * with a new [ArrayList] as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * Best suited for validating a single value
 * when no custom storage/context is required.
 *
 * @param rules The rules to apply to receiver object.
 * @return A [ValidationResult] containing all [Violation]s triggered by the [rules].
 */
public fun <T> T.validateAllWithRules(vararg rules: Rule<T>): ValidationResult =
    this.validateAllWithRulesTo(
        ArrayList(),
        rules = rules,
    )

/**
 * Executes a [block] that may perform validation, returning the result or a failure.
 *
 * If no [Violation]s occur, returns [Result.success] with the [block]’s return value.
 * Otherwise, returns [Result.failure] wrapping a [ValidationException].
 *
 * Internally creates a new [AggregatingValidationContext]
 * with a new [ArrayList] as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * @param block A lambda with validation and business logic to execute.
 * @return A [Result] containing either the [block]’s result or a [ValidationException].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> runValidatingAll(block: AggregatingValidationContext.() -> T): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return runValidatingAllTo(ArrayList(), block)
}

// ----Validate using AggregatingValidationContext with provided destination as violationStorage----

/**
 * Runs a [block] of validation logic
 * and collects all [Violation]s into the given [destination].
 *
 * Internally creates a new [AggregatingValidationContext] with the provided [destination]
 * as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * Best suited for validating multiple values or complex validation flows
 * when a custom storage is required.
 *
 * @param destination The collection used to store [Violation]s.
 * @param block A lambda with validation logic to execute.
 * @return A [ValidationResult] containing all collected [Violation]s.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllUsing(AggregatingValidationContext(destination), block)
}

/**
 * Applies the given [rules] to the receiver [T]
 * and stores [Violation]s in the provided [destination].
 *
 * Internally creates a new [AggregatingValidationContext] with the provided [destination]
 * as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * Best suited for validating a single value
 * when a custom storage is required.
 *
 * @param destination The collection used to store [Violation]s.
 * @param rules The rules to apply to the receiver object.
 * @return A [ValidationResult] containing all [Violation]s triggered by the [rules].
 */
public fun <T> T.validateAllWithRulesTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.validateAllWithRulesUsing(
        AggregatingValidationContext(destination),
        rules = rules,
    )

/**
 * Executes a [block] that may perform validation, returning the result or a failure.
 *
 * If no [Violation]s occur, returns [Result.success] with the [block]’s return value.
 * Otherwise, returns [Result.failure] wrapping a [ValidationException].
 *
 * Internally creates a new [AggregatingValidationContext] with the provided [destination]
 * as [AggregatingValidationContext.violationsStorage] to store [Violation]s.
 *
 * @param destination The collection used to store [Violation]s.
 * @param block A lambda with validation and business logic to execute.
 * @return A [Result] containing the block result or a [ValidationException].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> runValidatingAllTo(
    destination: MutableCollection<Violation>,
    block: AggregatingValidationContext.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return runValidatingAllUsing(AggregatingValidationContext(destination), block)
}

// ----Validate using provided context----

/**
 * Runs a [block] of validation logic using the provided [context]
 * to collect all [Violation]s.
 *
 * Enables reuse of an existing [AggregatingValidationContext], allowing
 * custom or shared violation storage.
 *
 * Best suited for validating multiple values or complex validation flows
 * when a custom context or violation storage is required.
 *
 * @param context The validation context to run the block within.
 * @param block A lambda with validation logic to execute.
 * @return A [ValidationResult] containing all collected [Violation]s.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <C : AggregatingValidationContext> validateAllUsing(
    context: C,
    block: C.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return context.apply(block).build()
}

/**
 * Applies the given [rules] to the receiver [T] using the provided [context]
 * to collect all [Violation]s.
 *
 * Best suited for validating a single value when validation rules
 * when a custom context or violation storage is required.
 *
 * @param context The validation context used to accumulate violations.
 * @param rules The rules to apply to the receiver object.
 * @return A [ValidationResult] containing all [Violation]s triggered by the rules.
 */
public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult =
    validateAllUsing(context) {
        this@validateAllWithRulesUsing.applyRules(rules = rules)
    }

/**
 * Executes a [block] that may perform validation, returning the result or a failure.
 *
 * If no [Violation]s occur, returns [Result.success] with the [block]’s return value.
 * Otherwise, returns [Result.failure] wrapping a [ValidationException].
 *
 * Uses the provided [context] to store [Violation]s.
 *
 * @param context The validation context used to collect violations.
 * @param block The logic block to execute.
 * @return A [Result] containing success with the block result or failure due to validation errors.
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T, C : AggregatingValidationContext> runValidatingAllUsing(
    context: C,
    block: C.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val result = context.run(block)

    return context.build().fold(
        ifValid = { Result.success(result) },
        ifInvalid = {
            val exception = ValidationException(it)
            Result.failure(exception)
        },
    )
}
