package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.failIf
import io.github.kverify.core.verification.Verification
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.OneOfViolation

public fun <T, V : Verification<T>> V.equalTo(
    expected: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value != expected }) {
            EqualToViolation(
                expected = expected,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be equal to $expected. Actual: $value",
            )
        }
    }

public fun <T, V : Verification<T>> V.notEqualTo(
    forbidden: T,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value == forbidden }) {
            NotEqualToViolation(
                forbidden = forbidden,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be equal to $forbidden",
            )
        }
    }

public fun <T, V : Verification<T>> V.oneOf(
    allowed: Set<T>,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value !in allowed }) {
            OneOfViolation(
                allowed = allowed,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must be one of $allowed. Actual: $value",
            )
        }
    }

public fun <T, V : Verification<T>> V.noneOf(
    forbidden: Set<T>,
    reason: String? = null,
): V =
    apply {
        scope.failIf({ value in forbidden }) {
            NoneOfViolation(
                forbidden = forbidden,
                actual = value,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
            )
        }
    }
