package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

@OptIn(ExperimentalExtendedContracts::class)
public inline fun ValidationStrategy.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
        condition.holdsIn(lazyViolation)
    }

    if (condition) {
        val violation = lazyViolation()

        failWith(violation)
    }
}
