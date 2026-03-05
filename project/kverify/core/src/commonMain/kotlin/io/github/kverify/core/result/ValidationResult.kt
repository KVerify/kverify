package io.github.kverify.core.result

import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

@JvmInline
public value class ValidationResult(
    public val violations: List<Violation>,
) {
    public inline val isValid: Boolean get() = violations.isEmpty()
    public inline val isInvalid: Boolean get() = !isValid
}

public fun ValidationResult.throwIfInvalid() {
    if (isValid) return

    throw ValidationException(violations)
}
