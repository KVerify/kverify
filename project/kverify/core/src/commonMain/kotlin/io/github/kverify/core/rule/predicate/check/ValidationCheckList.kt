package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * A composite [ValidationCheck] that wraps multiple checks with `AND` logic.
 *
 * A value is valid only if it passes all wrapped [checks].
 *
 * @property checks The list of checks to evaluate
 */
@JvmInline
public value class ValidationCheckList<in T>(
    public val checks: List<ValidationCheck<T>>,
) : ValidationCheck<T> {
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
 * @return empty [ValidationCheckList].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(): ValidationCheckList<T> =
    ValidationCheckList(
        checks = emptyList(),
    )

/**
 * @return [ValidationCheckList] containing a single [check].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(check: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = listOf(check),
    )

/**
 * @return [ValidationCheckList] containing the given [checks].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(vararg checks: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = checks.asList(),
    )

/**
 * Combines `this` check list with another [ValidationCheck].
 *
 * If [other] is a [ValidationCheckList], its [checks][ValidationCheckList.checks] are flattened into the result.
 * Otherwise, [other] is appended as a single check.
 *
 * ### Example:
 * ```kt
 * val checkList1 = ValidationCheckList(check1, check2)
 * val checkList2 = ValidationCheckList(check3, check4)
 *
 * // ValidationCheckList(check1, check2, check3)
 * checkList1 + check3
 *
 * // ValidationCheckList(check1, check2, check3, check4)
 * checkList1 + checkList2
 * ```
 *
 * @param other The check to add
 * @return A new check list containing all checks
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheck<T>): ValidationCheckList<T> =
    when (other) {
        is ValidationCheckList<T> -> ValidationCheckList(checks + other.checks)
        else -> ValidationCheckList(checks + other)
    }

/**
 * Combines `this` check list with another [ValidationCheckList].
 *
 * The [checks][ValidationCheckList.checks] from both lists are flattened into a single list.
 *
 * ### Example:
 * ```kt
 * val checkList1 = ValidationCheckList(check1, check2)
 * val checkList2 = ValidationCheckList(check3, check4)
 *
 * // ValidationCheckList(check1, check2, check3, check4)
 * checkList1 + checkList2
 * ```
 *
 * @param other The check list to add
 * @return A new check list containing all checks from both lists
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheckList<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks + other.checks,
    )
