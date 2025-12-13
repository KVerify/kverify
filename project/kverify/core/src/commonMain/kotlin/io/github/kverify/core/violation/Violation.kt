package io.github.kverify.core.violation

/**
 * Represents a validation failure with a [reason] of why it failed.
 *
 * @see ViolationReason
 */
public interface Violation {
    /**
     * A human-readable description of why the validation failed.
     */
    public val reason: String
}
