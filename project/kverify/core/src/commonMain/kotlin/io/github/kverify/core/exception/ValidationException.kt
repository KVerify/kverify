package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

public open class ValidationException(
    public val violations: List<Violation>,
) : RuntimeException(
        "Validation failed with ${violations.size} violation(s):\n" +
            violations.joinToString("\n") { "  - $it" },
    )
