package io.github.kverify.core.violation

public interface Violation {
    public val reason: String
}

public fun violation(reason: String): Violation = ViolationImpl(reason)

private class ViolationImpl(
    override val reason: String,
) : Violation {
    override fun toString(): String = "Violation(reason=$reason)"
}
