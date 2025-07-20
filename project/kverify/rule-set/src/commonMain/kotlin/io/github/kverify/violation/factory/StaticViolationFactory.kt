package io.github.kverify.violation.factory

import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

@JvmInline
public value class StaticViolationFactory(
    public val violation: Violation,
) : ViolationFactory<Any?> {
    override fun createViolation(value: Any?): Violation = violation
}
