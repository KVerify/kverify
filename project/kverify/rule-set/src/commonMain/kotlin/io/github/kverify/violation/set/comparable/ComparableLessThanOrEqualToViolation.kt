package io.github.kverify.violation.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveComparableName

public data class ComparableLessThanOrEqualToViolation<T : Comparable<T>>(
    val value: T,
    val min: T,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveComparableName(name)

            "$displayName must be less than or equal to '$min', but it is '$value'."
        },
) : Violation
