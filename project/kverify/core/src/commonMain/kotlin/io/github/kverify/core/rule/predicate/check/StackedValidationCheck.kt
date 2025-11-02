package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * Composite [ValidationCheck] that executes a list of inner [checks] sequentially.
 */
@JvmInline
public value class StackedValidationCheck<in T>(
    public val checks: List<ValidationCheck<T>>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = checks.all { it.isValid(value) }
}

/**
 * Creates an empty [StackedValidationCheck].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedValidationCheck(): StackedValidationCheck<T> =
    StackedValidationCheck(
        checks = emptyList(),
    )

/**
 * Creates a [StackedValidationCheck] containing a single [check].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedValidationCheck(check: ValidationCheck<T>): StackedValidationCheck<T> =
    StackedValidationCheck(
        checks = listOf(check),
    )

/**
 * Creates a [StackedValidationCheck] from the provided [checks].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> StackedValidationCheck(vararg checks: ValidationCheck<T>): StackedValidationCheck<T> =
    StackedValidationCheck(
        checks = checks.asList(),
    )

/**
 * Combines `this` [StackedValidationCheck] with another [ValidationCheck], producing a new [StackedValidationCheck]
 * that executes all checks in sequence.
 *
 * If the [other] check is itself a [StackedValidationCheck], its checks are flattened into the resulting sequence.
 * The checks from `this` [StackedValidationCheck] are executed first, followed by the checks from [other].
 */
public operator fun <T> StackedValidationCheck<T>.plus(other: ValidationCheck<T>): StackedValidationCheck<T> =
    when (other) {
        is StackedValidationCheck<T> -> StackedValidationCheck(checks + other.checks)
        else -> StackedValidationCheck(checks + other)
    }

/**
 * Combines `this` [StackedValidationCheck] with another [StackedValidationCheck], producing a new [StackedValidationCheck]
 * that executes all checks from both stacks in sequence.
 *
 * The checks from `this` [StackedValidationCheck] are executed first, followed by the checks from [other].
 */
public operator fun <T> StackedValidationCheck<T>.plus(other: StackedValidationCheck<T>): StackedValidationCheck<T> =
    StackedValidationCheck(
        checks + other.checks,
    )
