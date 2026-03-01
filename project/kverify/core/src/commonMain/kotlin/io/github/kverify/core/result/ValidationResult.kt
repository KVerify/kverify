package io.github.kverify.core.result

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation

public sealed interface ValidationResult {
    public object Valid : ValidationResult {
        override fun toString(): String = "ValidationResult.Valid"
    }

    public class Invalid(
        public val violations: List<Violation>,
    ) : ValidationResult {
        override fun toString(): String = "ValidationResult.Invalid(violations=$violations)"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Invalid) return false
            return violations == other.violations
        }

        override fun hashCode(): Int = violations.hashCode()
    }
}

public fun ValidationResult(violations: List<Violation>): ValidationResult =
    if (violations.isEmpty()) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(
            violations = violations,
        )
    }

public fun ValidationResult.throwIfInvalid() {
    if (this is ValidationResult.Invalid) throw ValidationException(violations)
}

public inline fun ValidationResult.onValid(block: () -> Unit): ValidationResult =
    apply {
        if (this is ValidationResult.Valid) block()
    }

public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit): ValidationResult =
    apply {
        if (this is ValidationResult.Invalid) block(violations)
    }

public inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    when (this) {
        ValidationResult.Valid -> onValid()
        is ValidationResult.Invalid -> onInvalid(violations)
    }
