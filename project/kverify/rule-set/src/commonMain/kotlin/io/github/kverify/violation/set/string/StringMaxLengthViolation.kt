package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringMaxLengthViolation(
    val value: String,
    val max: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName length must be $max at most, but it is ${value.length}."
        },
) : Violation
