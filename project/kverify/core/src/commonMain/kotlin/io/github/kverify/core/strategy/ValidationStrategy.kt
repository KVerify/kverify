package io.github.kverify.core.strategy

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.ViolationRule
import io.github.kverify.core.violation.Violation

public interface ValidationStrategy {
    public fun enforce(rule: Rule)
}
