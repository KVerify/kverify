package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveStringName

public data class StringLengthBetweenViolation(
    val value: String,
    val minLength: Int,
    val maxLength: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveStringName(name)

            "$displayName must have a length between $minLength and $maxLength (inclusive), but its length is ${value.length}."
        },
) : Violation {
    public constructor(
        value: String,
        range: IntRange,
        name: String? = null,
    ) : this(
        value = value,
        minLength = range.first,
        maxLength = range.last,
        name = name,
    )

    public constructor(
        value: String,
        range: IntRange,
        name: String? = null,
        reason: String,
    ) : this(
        value = value,
        minLength = range.first,
        maxLength = range.last,
        name = name,
        reason = reason,
    )
}
