package io.github.kverify.core

import io.github.kverify.rule.Rule
import io.github.kverify.scope.ValidationScope

public interface Verification<T> {
    public fun enforce(rule: Rule<T>)
}

public class ScopedVerification<T>(
    public val value: T,
    public val scope: ValidationScope,
) : Verification<T> {
    override fun enforce(rule: Rule<T>): Unit = rule.execute(scope, value)
}

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> Verification<T>.using(rule: Rule<T>): Unit = enforce(rule)

@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> Verification<T>.using(rules: Iterable<Rule<T>>) {
    for (rule in rules) enforce(rule)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> Verification<T>.using(vararg rules: Rule<T>): Unit = using(rules.asList())

public fun <T : Any> ScopedVerification<T?>.ifNotNull(): ScopedVerification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ScopedVerification<T>
    } else {
        null
    }
