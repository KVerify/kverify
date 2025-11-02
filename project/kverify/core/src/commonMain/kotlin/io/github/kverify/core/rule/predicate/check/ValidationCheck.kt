package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * Predicate that determines whether a value is valid.
 */
public fun interface ValidationCheck<in T> {
    /**
     * Returns `true` when [value] satisfies the check.
     */
    public fun isValid(value: T): Boolean
}

/**
 * Returns an inverted [ValidationCheck] that negates the original result.
 */
@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

@PublishedApi
@JvmInline
internal value class InvertedValidationCheck<in T>(
    private val originalCheck: ValidationCheck<T>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = !originalCheck.isValid(value)
}
