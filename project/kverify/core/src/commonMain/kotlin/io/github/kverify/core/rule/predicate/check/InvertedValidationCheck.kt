package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

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
