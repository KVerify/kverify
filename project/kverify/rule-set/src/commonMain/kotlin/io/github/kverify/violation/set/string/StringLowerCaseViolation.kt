package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringLowerCaseViolation(
    val value: String,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)
            val upperCaseChars = value.asIterable().filterNot { it.isLowerCase() }.joinWithLimitAndBrackets()

            "$displayName must be entirely lower case, but it also contains upper case characters: $upperCaseChars."
        },
) : Violation
