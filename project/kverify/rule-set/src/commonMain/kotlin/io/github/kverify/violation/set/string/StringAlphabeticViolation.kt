package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringAlphabeticViolation(
    val value: String,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)
            val unexpectedChars = value.asIterable().filterNot { it.isLetter() }.joinWithLimitAndBrackets()

            "$displayName must contain only letters, but it also contains: $unexpectedChars."
        },
) : Violation
