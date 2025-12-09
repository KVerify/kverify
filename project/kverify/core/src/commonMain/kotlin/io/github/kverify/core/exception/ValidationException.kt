package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * An exception that carries a list of [violations].
 */
public open class ValidationException(
    message: String? = null,
    public val violations: List<Violation> = emptyList(),
    cause: Throwable? = null,
) : Throwable(
        message = message,
        cause = cause,
    ) {
    /**
     * Creates a [ValidationException] with an auto-generated message from [violations].
     *
     * The message lists all violation reasons, or "Validation failed" if the list is empty.
     */
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
        violation: Violation,
        cause: Throwable? = null,
    ) : this(
        violations = listOf(violation),
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
