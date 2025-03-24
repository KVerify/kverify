package io.github.kverify.core.violation

import kotlin.jvm.JvmInline

/**
 * Represents a simple implementation of [Violation].
 *
 * Holds a [message] describing the violation.
 */
@JvmInline
public value class StringViolation(
    override val message: String,
) : Violation {
    override fun toString(): String = "StringViolation(message=$message)"
}

/**
 * Converts the given [String] into a [StringViolation].
 *
 * @return a [StringViolation] with the provided [String] as its message.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun String.asViolation(): StringViolation = StringViolation(this)
