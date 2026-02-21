package io.github.kverify.rule

import io.github.kverify.scope.ValidationScope
import kotlin.collections.plus
import kotlin.jvm.JvmInline

public fun interface ValidationCheck<in T> {
    public fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean
}

@Suppress("NOTHING_TO_INLINE")
public inline operator fun <T> ValidationCheck<T>.not(): ValidationCheck<T> = InvertedValidationCheck(this)

public infix fun <T> ValidationCheck<T>.and(other: ValidationCheck<T>): AllValidationCheckList<T> =
    when {
        this is AllValidationCheckList && other is AllValidationCheckList -> {
            AllValidationCheckList(this.checks + other.checks)
        }

        this is AllValidationCheckList -> {
            AllValidationCheckList(this.checks + other)
        }

        other is AllValidationCheckList -> {
            AllValidationCheckList(listOf(this) + other.checks)
        }

        else -> {
            AllValidationCheckList(this, other)
        }
    }

public infix fun <T> ValidationCheck<T>.or(other: ValidationCheck<T>): AnyValidationCheckList<T> =
    when {
        this is AnyValidationCheckList && other is AnyValidationCheckList -> {
            AnyValidationCheckList(this.checks + other.checks)
        }

        this is AnyValidationCheckList -> {
            AnyValidationCheckList(this.checks + other)
        }

        other is AnyValidationCheckList -> {
            AnyValidationCheckList(listOf(this) + other.checks)
        }

        else -> {
            AnyValidationCheckList(this, other)
        }
    }

public interface ValidationCheckList<in T> : ValidationCheck<T> {
    public val checks: List<ValidationCheck<T>>
}

@JvmInline
public value class InvertedValidationCheck<in T>(
    public val originalCheck: ValidationCheck<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = !originalCheck.isValid(scope, value)
}

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
