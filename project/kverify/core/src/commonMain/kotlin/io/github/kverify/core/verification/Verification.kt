package io.github.kverify.core.verification

import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
}

public fun Verification<*>.failWith(violation: Violation): Unit = validationStrategy.enforce(violation)
