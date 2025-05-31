package io.github.kverify.core.context

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.model.Rule
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Implementation of the [ValidationContext], that
 * collects [Violation]s reported via [ValidationContext.onFailure]
 * and stores them in [violationsStorage].
 */
public open class AggregatingValidationContext(
    public val violationsStorage: MutableCollection<Violation>,
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }

    /**
     * @return [ValidationResult] containing all [Violation]s from [violationsStorage].
     */
    public open fun build(): ValidationResult =
        ValidationResult(
            // toList creates a copy,
            // so we don't have to worry about concurrent modification
            violationsStorage.toList(),
        )
}

// ----Validate using AggregatingValidationContext with ArrayList as violationStorage----

/**
 * Executes the given [block] inside an [AggregatingValidationContext],
 * collecting any [Violation]s into a new [ArrayList].
 *
 * @return [ValidationResult] with all collected [Violation]s.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateAll(block: AggregatingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllTo(ArrayList(), block)
}

/**
 * Applies the given [rules] to the receiver value,
 * collecting any [Violation]s into a new [ArrayList].
 *
 * @return [ValidationResult] with all collected [Violation]s.
 */
public fun <T> T.validateAllWithRules(vararg rules: Rule<T>): ValidationResult =
    this.validateAllWithRulesTo(
        ArrayList(),
        rules = rules,
    )

/**
 * Runs the given [block] inside an [AggregatingValidationContext],
 * collecting any [Violation]s into a new [ArrayList]. Returns
 * [Result.success] if no violations, otherwise [Result.failure]
 * with a [ValidationException].
 *
 * @return [Result] wrapping either the block result or a [ValidationException].
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
 * Executes the given [block] inside an [AggregatingValidationContext],
 * collecting any [Violation]s into the provided [destination].
 *
 * @param destination collection to store [Violation]s
 * @return [ValidationResult] with all collected [Violation]s.
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
 * Applies the given [rules] to the receiver value,
 * collecting any [Violation]s into the provided [destination].
 *
 * @param destination collection to store [Violation]s
 * @return [ValidationResult] with all collected [Violation]s.
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
 * Runs the given [block] inside an [AggregatingValidationContext],
 * collecting any [Violation]s into the provided [destination].
 * Returns [Result.success] if no violations, otherwise [Result.failure]
 * with a [ValidationException].
 *
 * @param destination collection to store [Violation]s
 * @return [Result] wrapping either the block result or a [ValidationException].
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
 * Executes the given [block] inside the specified [context],
 * collecting any [Violation]s reported via [ValidationContext.onFailure].
 *
 * @param context the [AggregatingValidationContext] to use for collecting violations
 * @param block the validation logic to execute within the context
 * @return [ValidationResult] containing all collected violations after the block executes
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
 * Applies the given [rules] to the receiver value within the specified [context],
 * collecting any [Violation]s reported via [ValidationContext.onFailure].
 *
 * @param context the [AggregatingValidationContext] to use for collecting violations
 * @param rules the validation rules to apply
 * @return [ValidationResult] containing all collected violations after applying the rules
 */
public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult =
    validateAllUsing(context) {
        this@validateAllWithRulesUsing.applyRules(rules = rules)
    }

/**
 * Runs the given [block] inside the specified [context],
 * collecting any [Violation]s reported via [ValidationContext.onFailure].
 * Returns [Result.success] if no violations were collected,
 * otherwise [Result.failure] containing a [ValidationException] wrapping the violations.
 *
 * @param context the [AggregatingValidationContext] to use for collecting violations
 * @param block the validation logic to execute within the context
 * @return [Result] wrapping either the block result or a [ValidationException] with collected violations
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
    val violations = context.build().violations

    return if (violations.isEmpty()) {
        Result.success(result)
    } else {
        Result.failure(
            ValidationException(violations),
        )
    }
}
