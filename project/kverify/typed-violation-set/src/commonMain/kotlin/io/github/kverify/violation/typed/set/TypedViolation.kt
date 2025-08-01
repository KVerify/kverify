package io.github.kverify.violation.typed.set

import io.github.kverify.core.violation.Violation

public interface TypedViolation<T> : Violation {
    public val value: T
    public val name: String?
}
