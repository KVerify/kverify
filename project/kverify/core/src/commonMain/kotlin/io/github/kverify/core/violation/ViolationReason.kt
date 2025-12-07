package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

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
 * Wraps `this` [String] into a [ViolationReason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)

/**
 * Wraps given [reason] into a [ViolationReason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): ViolationReason = ViolationReason(reason)
