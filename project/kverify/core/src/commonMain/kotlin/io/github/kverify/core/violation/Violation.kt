package io.github.kverify.core.violation

/**
 * Representation of a validation failure.
 *
 * Implementations provide a human-readable [reason].
 */
public interface Violation {
    /**
     * Human-readable explanation of the violation.
     */
    public val reason: String
}
