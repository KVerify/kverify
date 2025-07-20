package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringEndsWithViolation(
    val value: String,
    val suffix: String,
    val ignoreCase: Boolean = false,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName must end with the following suffix (ignoreCase=$ignoreCase): $suffix, but it does not."
        },
) : Violation
