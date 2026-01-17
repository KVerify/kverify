package io.github.kverify.core.rule.predicate.check

import io.github.kverify.core.scope.ValidationScope
import kotlin.jvm.JvmInline

@JvmInline
public value class AllValidationCheckList<in T>(
    override val checks: List<ValidationCheck<T>>,
) : ValidationCheckList<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = checks.all { it.isValid(scope, value) }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = emptyList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(check: ValidationCheck<T>): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = listOf(check),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> AllValidationCheckList(vararg checks: ValidationCheck<T>): AllValidationCheckList<T> =
    AllValidationCheckList(
        checks = checks.asList(),
    )
