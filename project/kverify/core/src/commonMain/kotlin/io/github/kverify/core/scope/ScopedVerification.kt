package io.github.kverify.core.scope

import io.github.kverify.core.rule.Rule

public class ScopedVerification<T>(
    @PublishedApi
    internal val value: T,
    @PublishedApi
    internal val scope: ValidationScope,
)

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ScopedVerification<T>.using(vararg rules: Rule<T>) {
    for (rule in rules) rule.execute(scope, value)
}

public fun <T : Any> ScopedVerification<T?>.ifNotNull(): ScopedVerification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ScopedVerification<T>
    } else {
        null
    }
