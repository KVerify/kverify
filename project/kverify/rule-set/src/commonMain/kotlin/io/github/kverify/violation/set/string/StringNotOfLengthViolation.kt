package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringNotOfLengthViolation(
    val value: String,
    val length: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName must NOT be of length $length, but it is."
        },
) : Violation
