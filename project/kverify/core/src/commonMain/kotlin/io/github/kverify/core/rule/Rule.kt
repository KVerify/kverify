package io.github.kverify.core.rule

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation

public interface Rule<in T> {
    public fun check(
        context: ValidationContext,
        value: T,
    ): Violation?
}
