package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation

public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

context(validationStrategy: ValidationStrategy)
public fun failWith(violation: Violation): Unit = validationStrategy.failWith(violation)
