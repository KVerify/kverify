package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.EqualToViolation
import io.github.kverify.violations.NoneOfViolation
import io.github.kverify.violations.NotEqualToViolation
import io.github.kverify.violations.NotNullViolation
import io.github.kverify.violations.OneOfViolation

/**
 * Fails with [NotNullViolation] if the value is `null`.
 *
 * If [reason] is `null`, defaults to: `"Value must not be null"`.
 */
public fun <T> Verification<T>.notNull(reason: String? = null): Verification<T> =
    apply {
        scope.failIf({ value == null }) {
            NotNullViolation(
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be null",
            )
        }
    }

/**
 * Fails with [EqualToViolation] if the value does not equal [expected].
 *
 * If [reason] is `null`, defaults to: `"Value must be equal to $expected. Actual: $value"`.
 */
public fun <T> Verification<T>.equalTo(
    expected: T,
    reason: String? = null,
): Verification<T> =
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

/**
 * Fails with [NotEqualToViolation] if the value equals [forbidden].
 *
 * If [reason] is `null`, defaults to: `"Value must not be equal to $forbidden"`.
 */
public fun <T> Verification<T>.notEqualTo(
    forbidden: T,
    reason: String? = null,
): Verification<T> =
    apply {
        scope.failIf({ value == forbidden }) {
            NotEqualToViolation(
                forbidden = forbidden,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Value must not be equal to $forbidden",
            )
        }
    }

/**
 * Fails with [OneOfViolation] if the value is not in [allowed].
 *
 * If [reason] is `null`, defaults to: `"Value must be one of $allowed. Actual: $value"`.
 */
public fun <T> Verification<T>.oneOf(
    allowed: Set<T>,
    reason: String? = null,
): Verification<T> =
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

/**
 * Fails with [NoneOfViolation] if the value is in [forbidden].
 *
 * If [reason] is `null`, defaults to: `"Value must not be one of $forbidden. Actual: $value"`.
 */
public fun <T> Verification<T>.noneOf(
    forbidden: Set<T>,
    reason: String? = null,
): Verification<T> =
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
