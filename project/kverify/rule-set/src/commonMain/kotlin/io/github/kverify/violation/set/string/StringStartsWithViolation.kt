package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringStartsWithViolation(
    val value: String,
    val prefix: String,
    val ignoreCase: Boolean = false,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName must start with the following prefix (ignoreCase=$ignoreCase): '$prefix', but it does not."
        },
) : Violation
