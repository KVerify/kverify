package io.github.kverify.core.violation

public interface Violation {
    public val reason: String
}

public fun violation(reason: String): Violation = ViolationImpl(reason)

internal class ViolationImpl(
    override val reason: String,
) : Violation {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ViolationImpl) return false

        return reason == other.reason
    }

    override fun hashCode(): Int = reason.hashCode()

    override fun toString(): String = "Violation(reason=$reason)"
}
