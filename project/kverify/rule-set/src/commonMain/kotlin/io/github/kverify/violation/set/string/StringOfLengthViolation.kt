package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringOfLengthViolation(
    val value: String,
    val length: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName length must be exactly $length, but it is ${value.length}."
        },
) : Violation
