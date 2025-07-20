package io.github.kverify.violation.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveComparableName

public data class ComparableEqualToViolation<T : Comparable<T>>(
    val value: T,
    val other: T,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveComparableName(name)

            "$displayName must be equal to '$other', but it is '$value'."
        },
) : Violation
