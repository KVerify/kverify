package io.github.kverify.core

import kotlin.jvm.JvmInline

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

/**
 * A simple string-based implementation of [Violation].
 *
 * Wraps a string to provide a lightweight way to create violations.
 *
 * @property reason The human-readable description of the validation failure.
 */
@JvmInline
public value class ViolationReason(
    override val reason: String,
) : Violation

/**
 * @return [ViolationReason] with the given [reason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): ViolationReason = ViolationReason(reason)
