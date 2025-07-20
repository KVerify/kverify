package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringIsAlphanumericViolation(
    val value: String,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)
            val unexpectedChars = value.asIterable().filterNot { it.isLetterOrDigit() }.joinWithLimitAndBrackets()

            "$displayName must contain only numbers and letters, but it also contains: $unexpectedChars."
        },
) : Violation
