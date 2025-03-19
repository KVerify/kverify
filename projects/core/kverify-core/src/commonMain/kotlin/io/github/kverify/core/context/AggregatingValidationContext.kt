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
open class AggregatingValidationContext(
    val violationsStorage: MutableCollection<Violation> = mutableListOf(),
) : ValidationContext {
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }
}

/**
 * Executes the given [block] within an [AggregatingValidationContext] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: A copy of [violationsStorage] will be used in the [ValidationResult].
 *
 * @return [ValidationResult] containing all [Violation]s from [violationsStorage].
 */
@OptIn(ExperimentalContracts::class)
inline fun validateAll(
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
    block: AggregatingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ValidationResult(
        AggregatingValidationContext(violationsStorage)
            .apply(block)
            .violationsStorage
            .toList(),
    )
}

/**
 * Applies all [rules] to this value within an [AggregatingValidationContext] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: This function will create a copy of [violationsStorage] to use in [ValidationResult]
 *
 * @return [ValidationResult] containing all collected [Violation]s.
 */
fun <T> T.validateAllWithRules(
    violationsStorage: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    validateAll(violationsStorage) lambda@{
        this@validateAllWithRules.applyRules(rules = rules)
    }

/**
 * Applies all [rules] to this value within an [AggregatingValidationContext] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in a new [MutableList].
 *
 * @return [ValidationResult] containing all collected [Violation]s.
 */
fun <T> T.validateAllWithRules(vararg rules: Rule<T>): ValidationResult =
    validateAll(
        violationsStorage = mutableListOf(),
    ) lambda@{
        this@validateAllWithRules.applyRules(rules = rules)
    }

/**
 * Runs given [block] within an [AggregatingValidationContext] context,
 * collecting [Violation]s reported via [ValidationContext.onFailure]
 * and storing them in [violationsStorage].
 *
 * Note: This function will create a copy of [violationsStorage] to use in [ValidationResult]
 *
 * @return [Result.success] if [violationsStorage] is empty,
 * otherwise returns [Result.failure] wrapping a [ValidationException]
 * with all collected [Violation]s
 */
@OptIn(ExperimentalContracts::class)
inline fun <T> runValidatingAll(
    violationsStorage: MutableCollection<Violation> = mutableListOf(),
    block: AggregatingValidationContext.() -> T,
): Result<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val aggregatingValidationContext = AggregatingValidationContext(violationsStorage)
    val result = aggregatingValidationContext.run(block)

    val violations = aggregatingValidationContext.violationsStorage.toList()

    return if (violations.isEmpty()) {
        Result.success(result)
    } else {
        Result.failure(
            ValidationException(violations),
        )
    }
}
