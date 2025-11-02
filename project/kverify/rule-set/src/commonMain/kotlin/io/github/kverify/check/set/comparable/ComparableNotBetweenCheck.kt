package io.github.kverify.check.set.comparable

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class ComparableNotBetweenCheck<T : Comparable<T>>(
    public val range: ClosedRange<T>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = value !in range
}
