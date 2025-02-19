package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

/**
 * Represents a simple implementation of [Violation].
 *
 * Holds a [message] describing the violation.
 */
@JvmInline
value class AnyViolation(
    override val message: String,
) : Violation {
    override fun toString(): String = "AnyViolation(message=$message)"
}

/**
 * Converts the given [String] into an [AnyViolation].
 *
 * @return an [AnyViolation] with the provided [String] as its message.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun String.asViolation(): AnyViolation = AnyViolation(this)
