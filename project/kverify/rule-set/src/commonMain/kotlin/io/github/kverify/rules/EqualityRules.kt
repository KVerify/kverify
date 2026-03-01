package io.github.kverify.rules

import io.github.kverify.core.scope.ScopedVerification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.OneOfViolation

public fun <T> ScopedVerification<T>.equalTo(
    expected: T,
    reason: String? = null,
): ScopedVerification<T> =
    apply {
        scope.failIf(value != expected) {
            EqualToViolation(
                expected = expected,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be equal to $expected. Actual: $value",
            )
        }
    }

public fun <T> ScopedVerification<T>.notEqualTo(
    forbidden: T,
    reason: String? = null,
): ScopedVerification<T> =
    apply {
        scope.failIf(value == forbidden) {
            NotEqualToViolation(
                forbidden = forbidden,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be equal to $forbidden",
            )
        }
    }

public fun <T> ScopedVerification<T>.oneOf(
    allowed: Set<T>,
    reason: String? = null,
): ScopedVerification<T> =
    apply {
        scope.failIf(value !in allowed) {
            OneOfViolation(
                allowed = allowed,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be one of $allowed. Actual: $value",
            )
        }
    }

public fun <T> ScopedVerification<T>.noneOf(
    forbidden: Set<T>,
    reason: String? = null,
): ScopedVerification<T> =
    apply {
        scope.failIf(value in forbidden) {
            NoneOfViolation(
                forbidden = forbidden,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
            )
        }
    }
