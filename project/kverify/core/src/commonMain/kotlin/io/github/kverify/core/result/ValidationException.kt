package io.github.kverify.core.result

import io.github.kverify.core.violation.Violation

public open class ValidationException(
    public val violations: List<Violation>,
) : RuntimeException() {
    override val message: String get() = "Validation failed with ${violations.size} violation(s)"
}

public class ViolationException(
    public val violation: Violation,
) : ValidationException(listOf(violation))
