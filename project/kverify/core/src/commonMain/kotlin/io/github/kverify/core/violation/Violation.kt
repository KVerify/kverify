package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

public interface Violation {
    public val reason: String
}

@JvmInline
@PublishedApi
internal value class ViolationReason(
    override val reason: String,
) : Violation

@Suppress("NOTHING_TO_INLINE")
public inline fun violation(reason: String): Violation = ViolationReason(reason)
