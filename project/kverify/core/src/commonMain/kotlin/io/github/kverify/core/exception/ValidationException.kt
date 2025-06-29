package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import kotlin.jvm.JvmName

/**
 * An exception that represents validation failures with detailed violation information.
 *
 * This exception is the primary error type thrown by validation frameworks when validation rules fail.
 * It encapsulates one or more [Violation] objects that describe the specific validation failures,
 * providing rich error information for debugging and user feedback.
 *
 * The exception supports both single and multiple violations, making it suitable for both
 * fail-fast validation scenarios and comprehensive validation that collects all errors.
 *
 * @property violations The list of validation violations that caused this exception.
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
 * Creates a [ValidationException] with automatically formatted error messages from violations.
 *
 * Constructs a [ValidationException] with a comprehensive error message that includes
 * all violation details. The message is automatically formatted to be human-readable,
 * with each violation listed on a separate line with proper indentation.
 *
 * If no violations are provided, uses a default "Validation failed" message.
 * Otherwise, creates a detailed message showing all violation reasons in a structured format.
 *
 * Best suited for scenarios where you want comprehensive error messages without
 * manually formatting violation details.
 *
 * @param violations The list of validation violations that caused the failure.
 * @param cause An optional underlying cause of the validation failure.
 * @return A [ValidationException] with formatted error messages and violation details.
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

/**
 * Creates a [ValidationException] from individual violation arguments.
 *
 * Constructs a [ValidationException] by accepting individual [Violation] objects as vararg parameters.
 * This provides a convenient way to create exceptions with multiple violations without
 * explicitly creating a list.
 *
 * Internally converts the vararg violations to a list and delegates to the list-based constructor,
 * ensuring consistent message formatting and behavior.
 *
 * Best suited for scenarios where you have a known, small number of violations
 * and want to avoid explicit list creation.
 *
 * @param violations Individual violation objects that caused the validation failure.
 * @param cause An optional underlying cause of the validation failure.
 * @return A [ValidationException] containing all provided violations.
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
 * Creates a [ValidationException] from string messages with custom error message.
 *
 * Constructs a [ValidationException] by converting string messages into [Violation] objects
 * while allowing for a custom overall error message. This provides flexibility when you
 * want to control both the exception message and the individual violation details.
 *
 * Each string in [violationMessages] is converted to a [ViolationReason] using the
 * [asViolationReason] extension function, creating structured violation objects
 * from simple string descriptions.
 *
 * Best suited for scenarios where you have string-based error messages
 * and want to maintain a custom exception message separate from violation details.
 *
 * @param message The custom error message for the exception.
 * @param violationMessages List of string messages describing individual violations.
 * @param cause An optional underlying cause of the validation failure.
 * @return A [ValidationException] with custom message and converted violations.
 */
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

/**
 * Creates a [ValidationException] from string messages with automatic formatting.
 *
 * Constructs a [ValidationException] by converting string messages into [Violation] objects
 * and automatically generating a formatted error message. This is the most convenient
 * way to create validation exceptions from simple string descriptions.
 *
 * Each string in [violationMessages] is converted to a [ViolationReason] using the
 * [asViolationReason] extension function, then the standard formatting logic is applied
 * to create a comprehensive error message.
 *
 * Best suited for scenarios where you have string-based error messages
 * and want automatic formatting without manual message construction.
 *
 * @param violationMessages List of string messages describing individual violations.
 * @param cause An optional underlying cause of the validation failure.
 * @return A [ValidationException] with automatically formatted message and converted violations.
 */
@JvmName("ValidationExceptionFromMessages")
public fun ValidationException(
    violationMessages: List<String> = emptyList(),
    cause: Throwable? = null,
): ValidationException =
    ValidationException(
        violationMessages.map { it.asViolationReason() },
        cause,
    )
