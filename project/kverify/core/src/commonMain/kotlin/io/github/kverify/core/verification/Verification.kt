package io.github.kverify.core.verification

import io.github.kverify.core.rule.ViolationRule
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
}

public inline fun Verification<*>.failIf(
    crossinline predicate: () -> Boolean,
    crossinline lazyViolation: () -> Violation,
): Unit =
    validationStrategy.enforce {
        if (predicate()) {
            lazyViolation()
        } else {
            null
        }
    }

public fun Verification<*>.failWith(violation: Violation): Unit =
    validationStrategy.enforce(
        ViolationRule(violation),
    )
