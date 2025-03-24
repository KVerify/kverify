package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolation
import kotlin.jvm.JvmName

/**
 * Represents an exception thrown during validation with a list of [violations].
 */
public open class ValidationException(
    message: String? = null,
    public val violations: List<Violation> = emptyList(),
    cause: Throwable? = null,
) : Throwable(
        message = message,
        cause = cause,
    )

/**
 * Creates a [ValidationException] containing the given [violations] and an optional [cause].
 *
 * - If [violations] list is not empty, their [Violation.message]s are joined into a single string,
 *   separated by newlines.
 * - If [violations] list is empty, the default message is `"Validation failed"`.
 */
public fun ValidationException(
    violations: List<Violation>,
    cause: Throwable? = null,
): ValidationException {
    val message =
        if (violations.isEmpty()) {
            "Validation failed"
        } else {
            "Validation failed: \n${
                violations.joinToString("\n") {
                    "\t- ${it.message}"
                }
            }"
        }

    return ValidationException(
        violations = violations,
        message = message,
        cause = cause,
    )
}

/**
 * Creates a [ValidationException] containing the given [violations] and an optional [cause].
 */
public fun ValidationException(
    vararg violations: Violation,
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violations.asList(),
        cause,
    )

/**
 * Creates a [ValidationException] with the given [message] and [violationMessages],
 * mapping the [violationMessages] to [io.github.kverify.core.violation.StringViolation] objects.
 */
public fun ValidationException(
    message: String? = null,
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        message,
        violationMessages.map { it.asViolation() },
        cause,
    )

/**
 * Creates a [ValidationException] from the given [violationMessages],
 * mapping them to [io.github.kverify.core.violation.StringViolation] objects.
 */
@JvmName("ValidationExceptionFromMessages")
public fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolation() },
        cause,
    )
