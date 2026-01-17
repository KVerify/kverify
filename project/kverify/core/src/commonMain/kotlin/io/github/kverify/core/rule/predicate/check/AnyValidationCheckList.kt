package io.github.kverify.core.rule.predicate.check

import io.github.kverify.core.scope.ValidationScope
import kotlin.jvm.JvmInline

@JvmInline
public value class AnyValidationCheckList<in T>(
    override val checks: List<ValidationCheck<T>>,
) : ValidationCheckList<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = checks.any { it.isValid(scope, value) }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = emptyList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(check: ValidationCheck<T>): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = listOf(check),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AnyValidationCheckList(vararg checks: ValidationCheck<T>): AnyValidationCheckList<T> =
    AnyValidationCheckList(
        checks = checks.asList(),
    )
