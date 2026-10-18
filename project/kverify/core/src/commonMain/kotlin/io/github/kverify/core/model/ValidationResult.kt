package io.github.kverify.core.model

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation

public class ValidationResult(
    public val violations: List<Violation>,
) {
    public inline val isValid: Boolean get() = violations.isEmpty()

    public inline val isInvalid: Boolean get() = !isValid

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is ValidationResult -> false
            else -> violations == other.violations
        }

    override fun hashCode(): Int = violations.hashCode()

    override fun toString(): String = "ValidationResult(violations=$violations)"
}

public inline fun ValidationResult.onValid(block: () -> Unit) {
    if (isValid) block()
}

public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit) {
    if (isInvalid) block(violations)
}

public inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

public fun ValidationResult.throwOnInvalid(): Unit =
    onInvalid {
        throw ValidationException(it)
    }
