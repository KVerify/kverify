package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation

/**
 * Defines what happens when a violation is reported.
 */
public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}
