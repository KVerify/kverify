package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * Thrown when validation fails, carrying all [violations] that were collected before the throw.
 *
 * The subtype [ViolationException] is available for cases where exactly one violation is present.
 *
 * @param violations All violations produced during the validation run.
 */
public open class ValidationException(
    public val violations: List<Violation>,
) : RuntimeException() {
    override val message: String get() = "Validation failed with ${violations.size} violation(s)"
}
