package io.github.kverify.core.strategy

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

public interface ValidationStrategy {
    public fun enforce(rule: Rule)
}

context(validationStrategy: ValidationStrategy)
public inline fun failIf(
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
