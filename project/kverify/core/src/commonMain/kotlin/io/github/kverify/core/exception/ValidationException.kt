package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * An exception that indicates failed validation. Holds the [violations] that were found.
 */
public open class ValidationException(
    public val violations: List<Violation>,
) : RuntimeException("Validation failed with ${violations.size} violation(s)")
