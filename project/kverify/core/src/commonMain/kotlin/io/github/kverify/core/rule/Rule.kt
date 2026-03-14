package io.github.kverify.core.rule

import io.github.kverify.core.violation.Violation

/**
 * A single validation check that produces a [Violation] on failure.
 *
 * [Rule] is a functional interface and can be implemented either as a lambda or as a class.
 * The contract is simple:
 * - Return `null` if the check passes.
 * - Return a [Violation] if the check fails.
 *
 * Implementations must not throw — any exceptional condition that represents
 * a validation failure should be expressed as a [Violation] instead.
 */
public fun interface Rule {
    /**
     * Returns `null` if the check passes, or a [Violation] describing the failure.
     */
    public fun check(): Violation?
}
