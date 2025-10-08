package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * Exception thrown by contexts that abort validation by throwing on the first failure.
 *
 * Contains the single [violation] that caused the exception.
 */
public open class ThrowingValidationContextException(
    message: String? = null,
    public val violation: Violation,
    cause: Throwable? = null,
) : ValidationException(
        message = message,
        violations = listOf(violation),
        cause = cause,
    ) {
    public constructor(
        violation: Violation,
        cause: Throwable? = null,
    ) : this(
        message = "Validation failed:\n\t- ${violation.reason}",
        violation = violation,
        cause = cause,
    )
}
