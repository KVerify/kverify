package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringContainsViolation(
    val value: String,
    val subString: String,
    val ignoreCase: Boolean = false,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName must contain the following substring (ignoreCase=$ignoreCase): '$subString', but it does not."
        },
) : Violation {
    public constructor(
        value: String,
        char: Char,
        ignoreCase: Boolean = false,
        name: String? = null,
    ) : this(
        value = value,
        subString = char.toString(),
        ignoreCase = ignoreCase,
        name = name,
    )

    public constructor(
        value: String,
        char: Char,
        ignoreCase: Boolean = false,
        name: String? = null,
        reason: String,
    ) : this(
        value = value,
        subString = char.toString(),
        ignoreCase = ignoreCase,
        name = name,
        reason = reason,
    )
}
