package io.github.kverify.core.check

import kotlin.jvm.JvmInline

public fun interface ValidationCheck<in T> {
    public fun isValid(value: T): Boolean
}

@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

@JvmInline
public value class InvertedValidationCheck<in T>(
    private val originalCheck: ValidationCheck<T>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = !originalCheck.isValid(value)
}
