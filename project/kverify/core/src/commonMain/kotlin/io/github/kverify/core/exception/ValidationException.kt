package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import kotlin.jvm.JvmName

public open class ValidationException(
    message: String? = null,
    public val violations: List<Violation> = emptyList(),
    cause: Throwable? = null,
) : Throwable(
        message = message,
        cause = cause,
    )

public fun ValidationException(
    violations: List<Violation>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violations.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed:\n${
                violations.joinToString("\n") {
                    "\t- ${it.reason}"
                }
            }"
        }

    return ValidationException(
        violations = violations,
        message = message,
        cause = cause,
    )
}

public fun ValidationException(
    vararg violations: Violation,
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violations.asList(),
        cause,
    )

public fun ValidationException(
    message: String? = null,
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        message,
        violationMessages.map { it.asViolationReason() },
        cause,
    )

@JvmName("ValidationExceptionFromMessages")
public fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolationReason() },
        cause,
    )
