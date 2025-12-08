package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * A predicate that determines whether a value is valid.
 *
 * Implementations return `true` for valid values and `false` for invalid ones.
 * Used by [io.github.kverify.core.rule.predicate.PredicateRule] to perform validation.
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
 * Returns a new [ValidationCheck] that inverts the result of `this` check.
 *
 * Example:
 * ```kt
 * val isPositive = ValidationCheck<Int> { it > 0 }
 * val isNotPositive = !isPositive
 *
 * isPositive.isValid(5) // true
 * isNotPositive.isValid(5) // false
 * ```
 */
@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

/**
 * Wraps a [ValidationCheck] to invert its validation logic.
 *
 * @property originalCheck The check to invert
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
