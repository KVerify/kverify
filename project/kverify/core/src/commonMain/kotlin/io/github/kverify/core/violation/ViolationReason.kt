package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

/**
 * Represents a simple implementation of [Violation].
 *
 * Holds a [reason] describing the violation.
 */
@JvmInline
public value class ViolationReason(
    override val reason: String,
) : Violation {
    override fun toString(): String = "ViolationReason(reason=$reason)"
}

/**
 * Converts the given [String] into a [ViolationReason].
 *
 * @return a [ViolationReason] with the provided [String] as its reason.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)
