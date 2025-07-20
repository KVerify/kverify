package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringContainsAllViolation(
    val value: String,
    val chars: Iterable<Char>,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)
            val charsAsString = chars.joinWithLimitAndBrackets()
            val missingChars = value.asIterable().filterNot { it in chars }.joinWithLimitAndBrackets()

            "$displayName must contain all of the following characters: $charsAsString, but these are missing: $missingChars."
        },
) : Violation {
    public constructor(
        value: String,
        vararg chars: Char,
        name: String? = null,
    ) : this(
        value = value,
        chars = chars.asList(),
        name = name,
    )

    public constructor(
        value: String,
        vararg chars: Char,
        name: String? = null,
        reason: String,
    ) : this(
        value = value,
        chars = chars.asList(),
        name = name,
        reason = reason,
    )

    public constructor(
        value: String,
        string: String,
        name: String? = null,
    ) : this(
        value = value,
        chars = string.asIterable(),
        name = name,
    )
}
