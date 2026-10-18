package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

public class ViolationException(
    public val violation: Violation,
) : ValidationException(listOf(violation))
