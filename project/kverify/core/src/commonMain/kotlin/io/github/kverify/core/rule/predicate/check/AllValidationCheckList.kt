package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * A [ValidationCheckList] where ALL checks must pass (AND logic).
 *
 * A value is valid only if it passes all wrapped [checks].
 * Evaluation stops at the first check that returns `false`.
 *
 * ### Example:
 * ```kt
 * val isPositive = ValidationCheck<Int> { it > 0 }
 * val isEven = ValidationCheck<Int> { it % 2 == 0 }
 *
 * val allChecks = AllValidationCheckList(isPositive, isEven)
 *
 * allChecks.isValid(4)  // true - positive and even
 * allChecks.isValid(3)  // false - positive but odd
 * allChecks.isValid(-2) // false - even but negative
 * ```
 *
 * @property checks The list of checks to evaluate
 * @see ValidationCheckList
 * @see AnyValidationCheckList
 */
@JvmInline
public value class AllValidationCheckList<in T>(
    override val checks: List<ValidationCheck<T>>,
) : ValidationCheckList<T> {
    /**
     * Tests whether the given [value] passes all [checks].
     *
     * Stops execution at the first check that returns `false`.
     *
     * @return `true` if all checks pass, `false` otherwise
     */
    override fun isValid(value: T): Boolean = checks.all { it.isValid(value) }
}

/**
 * Creates an empty [AllValidationCheckList].
 *
 * An empty check list always returns `true` for any value.
 *
 * @return An empty [AllValidationCheckList]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = emptyList(),
    )

/**
 * Creates an [AllValidationCheckList] containing a single [check].
 *
 * @param check The single check to include
 * @return An [AllValidationCheckList] with one check
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(check: ValidationCheck<T>): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = listOf(check),
    )

/**
 * Creates an [AllValidationCheckList] containing the given [checks].
 *
 * ### Example:
 * ```kt
 * val checkList = AllValidationCheckList<Int>(
 *     { it > 0 },
 *     { it < 100 },
 *     { it % 2 == 0 }
 * )
 *
 * checkList.isValid(50)  // true
 * checkList.isValid(51)  // false - not even
 * checkList.isValid(-2)  // false - not positive
 * checkList.isValid(150) // false - not less than 100
 * ```
 *
 * @param checks The vararg of checks to include
 * @return An [AllValidationCheckList] containing all the checks
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(vararg checks: ValidationCheck<T>): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = checks.asList(),
    )
