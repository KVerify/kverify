package io.github.kverify.core.violation

/**
 * A failure reported during validation.
 */
public interface Violation {
    /**
     * Describes why the validation failed.
     */
    public val reason: String
}
