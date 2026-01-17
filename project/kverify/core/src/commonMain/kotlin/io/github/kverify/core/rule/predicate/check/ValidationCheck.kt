package io.github.kverify.core.rule.predicate.check

import io.github.kverify.core.scope.ValidationScope

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
