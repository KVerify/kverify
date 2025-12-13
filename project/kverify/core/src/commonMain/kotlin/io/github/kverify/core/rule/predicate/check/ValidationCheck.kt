package io.github.kverify.core.rule.predicate.check

import kotlin.collections.plus
import kotlin.jvm.JvmInline

/**
 * A predicate that determines whether a value is valid.
 *
 * Implementations return `true` for valid values and `false` for invalid ones.
 * Used by [PredicateRule][io.github.kverify.core.rule.predicate.PredicateRule] to perform validation.
 *
 * @see ValidationCheckList
 * @see io.github.kverify.core.rule.predicate.PredicateRule
 */
public fun interface ValidationCheck<in T> {
    /**
     * Tests whether the given [value] is valid.
     *
     * @return `true` if valid, `false` otherwise
     */
    public fun isValid(value: T): Boolean
}

/**
 * ### Example:
 * ```kt
 * val isPositive = ValidationCheck<Int> { it > 0 }
 * val isNotPositive = !isPositive
 *
 * isPositive.isValid(5) // true
 * isNotPositive.isValid(5) // false
 * ```
 *
 * @return a new [ValidationCheck] that inverts the result of `this` check.
 */
@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

/**
 * Combines `this` check with another check using `AND` logic.
 *
 * If either check is a [ValidationCheckList], its [checks][ValidationCheckList.checks] are flattened into the result.
 * Otherwise, both checks are combined into a new [ValidationCheckList].
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
 * @param other The check to combine with
 * @return A new [ValidationCheckList] that passes only if all checks pass
 */
public operator fun <T> ValidationCheck<T>.plus(other: ValidationCheck<T>): ValidationCheck<T> =
    when {
        this is ValidationCheckList && other is ValidationCheckList -> ValidationCheckList(this.checks + other.checks)
        this is ValidationCheckList -> ValidationCheckList(this.checks + other)
        else -> ValidationCheckList(this, other)
    }

/**
 * Wraps [originalCheck] to invert its validation logic.
 *
 * @see ValidationCheck
 */
@PublishedApi
@JvmInline
internal value class InvertedValidationCheck<in T>(
    private val originalCheck: ValidationCheck<T>,
) : ValidationCheck<T> {
    /**
     * Returns the inverse of the original check's result.
     */
    override fun isValid(value: T): Boolean = !originalCheck.isValid(value)
}
