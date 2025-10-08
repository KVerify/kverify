package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * Exception representing validation failures.
 *
 * Contains the list of [violations] and an optional message and cause.
 */
public open class ValidationException(
    message: String? = null,
    public val violations: List<Violation> = emptyList(),
    cause: Throwable? = null,
) : Throwable(
        message = message,
        cause = cause,
    ) {
    public constructor(
        violations: List<Violation>,
        cause: Throwable? = null,
    ) : this(
        message =
            if (violations.isEmpty()) {
                "Validation failed"
            } else {
                "Validation failed:\n${
                    violations.joinToString("\n") {
                        "\t- ${it.reason}"
                    }
                }"
            },
        violations = violations,
        cause = cause,
    )

    public constructor(
        vararg violations: Violation,
        cause: Throwable? = null,
    ) : this(
        violations = violations.asList(),
        cause = cause,
    )
}
