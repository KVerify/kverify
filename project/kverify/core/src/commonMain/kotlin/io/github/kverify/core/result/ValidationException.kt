package io.github.kverify.core.result

import io.github.kverify.core.rule.Violation

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

/**
 * A [io.github.kverify.core.result.ValidationException] that carries a single [violation].
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
