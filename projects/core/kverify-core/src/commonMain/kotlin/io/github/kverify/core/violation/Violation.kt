package io.github.kverify.core.violation

/**
 * Represents a validation violation.
 *
 * Contains a [message] describing the violation.
 */
public interface Violation {
    /**
     * Describes the violation.
     */
    public val message: String
}
