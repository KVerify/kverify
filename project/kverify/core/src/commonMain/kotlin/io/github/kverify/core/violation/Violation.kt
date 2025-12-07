package io.github.kverify.core.violation

/**
 * Represents a validation failure with a description of why it failed.
 *
 * Implementations provide a [reason] describing what constraint was violated.
 *
 * @see ViolationReason
 */
public interface Violation {
    /**
     * A human-readable description of why the validation failed.
     */
    public val reason: String
}
