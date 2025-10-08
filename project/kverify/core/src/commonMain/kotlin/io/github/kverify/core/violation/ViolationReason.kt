package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

/**
 * Simple [Violation] implementation wrapping a string [reason].
 */
@JvmInline
public value class ViolationReason(
    override val reason: String,
) : Violation

/**
 * Converts a string to a [ViolationReason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)
