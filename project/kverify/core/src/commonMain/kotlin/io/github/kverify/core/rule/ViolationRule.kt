package io.github.kverify.core.rule

import io.github.kverify.core.violation.Violation

public class ViolationRule(
    public val violation: Violation,
) : Rule {
    override fun check(): Violation = violation
}
