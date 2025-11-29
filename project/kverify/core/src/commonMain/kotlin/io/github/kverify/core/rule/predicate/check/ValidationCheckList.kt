package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

@JvmInline
public value class ValidationCheckList<in T>(
    public val checks: List<ValidationCheck<T>>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = checks.all { it.isValid(value) }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(): ValidationCheckList<T> =
    ValidationCheckList(
        checks = emptyList(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(check: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = listOf(check),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(vararg checks: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = checks.asList(),
    )

public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheck<T>): ValidationCheckList<T> =
    when (other) {
        is ValidationCheckList<T> -> ValidationCheckList(checks + other.checks)
        else -> ValidationCheckList(checks + other)
    }

public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheckList<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks + other.checks,
    )
