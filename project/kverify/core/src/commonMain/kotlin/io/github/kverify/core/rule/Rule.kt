package io.github.kverify.core.rule

import io.github.kverify.core.violation.Violation

public fun interface Rule {
    public fun check(): Violation?
}
