package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

@JvmInline
public value class ViolationReason(
    override val reason: String,
) : Violation

@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolationReason(): ViolationReason = ViolationReason(this)

@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): ViolationReason = ViolationReason(reason)
