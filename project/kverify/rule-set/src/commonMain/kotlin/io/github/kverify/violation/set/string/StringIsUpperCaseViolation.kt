package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringIsUpperCaseViolation(
    val value: String,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)
            val lowercaseChars = value.asIterable().filter { it.isLowerCase() }.joinWithLimitAndBrackets()

            "$displayName must be entirely upper case, but it also contains lowercase characters: $lowercaseChars."
        },
) : Violation
