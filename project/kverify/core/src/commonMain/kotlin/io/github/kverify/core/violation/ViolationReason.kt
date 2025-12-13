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
 * @return [ViolationReason] with `this` string as the [reason][ViolationReason.reason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)

/**
 * @return [ViolationReason] with the given [reason].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): ViolationReason = ViolationReason(reason)
