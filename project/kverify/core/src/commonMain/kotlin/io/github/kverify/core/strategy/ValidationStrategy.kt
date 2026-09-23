package io.github.kverify.core.strategy

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmName

public interface ValidationStrategy {
    public fun enforce(rule: Rule)
}

public inline fun ValidationStrategy.failIf(
    crossinline predicate: () -> Boolean,
    crossinline lazyViolation: () -> Violation,
): Unit =
    enforce {
        if (predicate()) {
            lazyViolation()
        } else {
            null
        }
    }

@JvmName("contextualizedFailIf")
context(validationStrategy: ValidationStrategy)
public inline fun failIf(
    crossinline predicate: () -> Boolean,
    crossinline lazyViolation: () -> Violation,
): Unit = validationStrategy.failIf(predicate, lazyViolation)
