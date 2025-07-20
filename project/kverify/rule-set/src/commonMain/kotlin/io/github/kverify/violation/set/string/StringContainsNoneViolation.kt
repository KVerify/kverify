package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveStringName

public data class StringContainsNoneViolation(
    val value: String,
    val chars: Iterable<Char>,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name ?: "comparable")
            val charsAsString = chars.joinWithLimitAndBrackets()
            val forbiddenChars = value.asIterable().filter { it in chars }.joinWithLimitAndBrackets()

            "$displayName must not contain any of the following characters: $charsAsString, but these are present: $forbiddenChars."
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
