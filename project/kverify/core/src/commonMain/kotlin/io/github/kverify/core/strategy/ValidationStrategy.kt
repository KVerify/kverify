package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmName

public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

public inline fun ValidationStrategy.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()

        failWith(violation)
    }
}
