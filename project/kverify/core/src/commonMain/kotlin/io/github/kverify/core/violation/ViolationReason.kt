package io.github.kverify.core.violation

public class ViolationReason(
    override val reason: String,
) : Violation {
    override fun equals(other: Any?): Boolean =
        (
            this === other
        ) || (
            other is ViolationReason && reason == other.reason
        )

    override fun hashCode(): Int = reason.hashCode()

    override fun toString(): String = "ViolationReason(reason=$reason)"
}
