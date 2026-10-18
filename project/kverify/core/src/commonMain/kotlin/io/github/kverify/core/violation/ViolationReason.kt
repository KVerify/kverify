package io.github.kverify.core.violation

public class ViolationReason(
    override val reason: String,
) : Violation {
    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is ViolationReason -> false
            else -> reason == other.reason
        }

    override fun hashCode(): Int = reason.hashCode()

    override fun toString(): String = "ViolationReason(reason=$reason)"
}
