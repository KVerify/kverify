package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

public inline fun ValidationStrategy.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
    }

    if (condition) {
        val violation = lazyViolation()

        failWith(violation)
    }
}
