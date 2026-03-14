package io.github.kverify.core.violation

/**
 * Represents a single validation failure.
 *
 * [Violation] is the fundamental unit of failed validation in KVerify.
 * Every failed rule produces exactly one [Violation], which is handled
 * according to the active [io.github.kverify.core.scope.ValidationScope] implementation.
 *
 * Implement this interface to define domain-specific violation types that carry
 * structured data beyond a plain message. For simple, one-off violations that do
 * not require structured data, use the [violation] factory function instead.
 */
public interface Violation {
    /**
     * A human-readable explanation of why validation failed.
     */
    public val reason: String
}

/**
 * Creates a [Violation] with the given [reason].
 */
public fun violation(reason: String): Violation = ViolationImpl(reason)

/**
 * Default implementation of [Violation].
 */
private class ViolationImpl(
    override val reason: String,
) : Violation {
    override fun toString(): String = "Violation(reason=$reason)"
}
