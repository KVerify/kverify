package io.github.kverify.violation.set.comparable

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveComparableName

public data class ComparableNotBetweenViolation<T : Comparable<T>>(
    val value: T,
    val min: T,
    val max: T,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveComparableName(name)

            "$displayName must not be between '$min' and '$max', but it is '$value'."
        },
) : Violation
