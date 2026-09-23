package io.github.kverify.core.strategy

import io.github.kverify.core.rule.Rule

public interface ValidationStrategy {
    public fun enforce(rule: Rule)
}
