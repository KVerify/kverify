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
 * Converts `this` string to a [ViolationReason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)

/**
 * Creates new [ViolationReason]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): ViolationReason = ViolationReason(reason)
