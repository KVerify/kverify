package io.github.kverify.violation.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveComparableName

public data class StringContainsRegexViolation(
    val value: String,
    val regex: Regex,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveComparableName(name)

            "$displayName must contain the following regex pattern: '${regex.pattern}', but it does not."
        },
) : Violation {
    public constructor(
        value: String,
        pattern: String,
        name: String? = null,
    ) : this(
        value = value,
        regex = pattern.toRegex(),
        name = name,
    )

    public constructor(
        value: String,
        pattern: String,
        name: String? = null,
        reason: String,
    ) : this(
        value = value,
        regex = pattern.toRegex(),
        name = name,
        reason = reason,
    )
}
