package io.github.kverify.check.set.comparable

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class ComparableLessThanOrEqualToCheck<T : Comparable<T>>(
    public val other: T,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = value <= other
}
