package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation

public interface ValidationStrategy {
    // TODO: rename?
    public fun enforce(violation: Violation)
}

context(validationStrategy: ValidationStrategy)
public fun failWith(violation: Violation): Unit = validationStrategy.enforce(violation)
