package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * A [ValidationCheckList] where ANY check must pass (OR logic).
 *
 * A value is valid if it passes at least one of the wrapped [checks].
 * Evaluation stops at the first check that returns `true`.
 *
 * ### Example:
 * ```kt
 * val isNegative = ValidationCheck<Int> { it < 0 }
 * val isGreaterThanHundred = ValidationCheck<Int> { it > 100 }
 *
 * val anyChecks = AnyValidationCheckList(isNegative, isGreaterThanHundred)
 *
 * anyChecks.isValid(-5)  // true - negative
 * anyChecks.isValid(150) // true - greater than 100
 * anyChecks.isValid(50)  // false - neither negative nor > 100
 * ```
 *
 * @property checks The list of checks to evaluate
 * @see ValidationCheckList
 * @see AllValidationCheckList
 */
@JvmInline
public value class AnyValidationCheckList<in T>(
    override val checks: List<ValidationCheck<T>>,
) : ValidationCheckList<T> {
    /**
     * Tests whether the given [value] passes at least one of the [checks].
     *
     * Stops execution at the first check that returns `true`.
     *
     * @return `true` if any check passes, `false` otherwise
     */
    override fun isValid(value: T): Boolean = checks.any { it.isValid(value) }
}

/**
 * Creates an empty [AnyValidationCheckList].
 *
 * An empty check list always returns `false` for any value.
 *
 * @return An empty [AnyValidationCheckList]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = emptyList(),
    )

/**
 * Creates an [AnyValidationCheckList] containing a single [check].
 *
 * @param check The single check to include
 * @return An [AnyValidationCheckList] with one check
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(check: ValidationCheck<T>): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = listOf(check),
    )

/**
 * Creates an [AnyValidationCheckList] containing the given [checks].
 *
 * ### Example:
 * ```kt
 * val checkList = AnyValidationCheckList(
 *     { it < 0 },
 *     { it > 100 },
 *     { it == 50 }
 * )
 *
 * checkList.isValid(-5)  // true - negative
 * checkList.isValid(150) // true - greater than 100
 * checkList.isValid(50)  // true - equals 50
 * checkList.isValid(25)  // false - none of the conditions match
 * ```
 *
 * @param checks The vararg of checks to include
 * @return An [AnyValidationCheckList] containing all the checks
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(vararg checks: ValidationCheck<T>): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = checks.asList(),
    )
