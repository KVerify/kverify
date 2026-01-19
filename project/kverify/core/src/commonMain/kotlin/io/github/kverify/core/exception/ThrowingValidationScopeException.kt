package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * A [ValidationException] that carries a single [violation].
 */
public open class ThrowingValidationScopeException(
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
