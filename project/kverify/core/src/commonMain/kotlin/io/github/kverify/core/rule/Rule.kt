package io.github.kverify.core.rule

import io.github.kverify.core.scope.ValidationScope

public interface Rule<in T> {
    public fun execute(
        scope: ValidationScope,
        value: T,
    )
}
