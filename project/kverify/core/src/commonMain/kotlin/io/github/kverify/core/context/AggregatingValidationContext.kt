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
 * [ValidationContext] that collects all reported [Violation]s into [violationsStorage].
 *
 * Use [build] to produce a [ValidationResult] from the collected violations.
 */
public open class AggregatingValidationContext(
    public val violationsStorage: MutableCollection<Violation>,
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }

    /**
     * Builds a [ValidationResult] reflecting the contents of [violationsStorage].
     */
    public open fun build(): ValidationResult =
        ValidationResult(
            violationsStorage.toList(),
        )
}

// ----Validate using AggregatingValidationContext with ArrayList as violationStorage----

/**
 * Validates a [block] of code and returns a [ValidationResult] containing all collected [Violation]s.
 */
@OptIn(ExperimentalContracts::class)
public inline fun validateAll(block: AggregatingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return validateAllTo(ArrayList(), block)
}

/**
 * Validates `this` value with [rules] and returns an aggregated [ValidationResult].
 */
public fun <T> T.validateAllWithRules(vararg rules: Rule<T>): ValidationResult =
    this.validateAllWithRulesTo(
        ArrayList(),
        rules = rules,
    )

/**
 * Validates a [block] of code and returns a [Result] containing the result of the [block] or a [ValidationException]
 * if any [Violation]s were collected.
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
 * Validates a [block] of code to the provided [destination]
 * and returns a [ValidationResult] containing all collected [Violation]s.
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
 * Validates `this` value with [rules] to the provided [destination]
 * and returns an aggregated [ValidationResult].
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
 * Validates a [block] of code to the provided [destination]
 * and returns a [Result] containing the result of the [block] or a [ValidationException]
 * if any [Violation]s were collected.
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
 * Validates a [block] of code using the provided [context]
 * and returns a [ValidationResult] containing all collected [Violation]s.
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
 * Validates `this` value with [rules] using the provided [context]
 * and returns an aggregated [ValidationResult].
 */
public fun <T, C : AggregatingValidationContext> T.validateAllWithRulesUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return validateAllUsing(context) {
        value.applyRules(rules = rules)
    }
}

/**
 * Validates a [block] of code using the provided [context]
 * and returns a [Result] containing the result of the [block] or a [ValidationException]
 * if any [Violation]s were collected.
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
