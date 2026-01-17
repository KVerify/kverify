package io.github.kverify.core.rule.predicate.check

import io.github.kverify.core.scope.ValidationScope
import kotlin.jvm.JvmInline

@JvmInline
public value class InvertedValidationCheck<in T>(
    public val originalCheck: ValidationCheck<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = !originalCheck.isValid(scope, value)
}
