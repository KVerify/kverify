package io.github.kverify.core.violation

/**
 * Represents a validation violation.
 *
 * Contains a [reason] describing the violation.
 */
public interface Violation {
    /**
     * Describes the violation.
     */
    public val reason: String
}
