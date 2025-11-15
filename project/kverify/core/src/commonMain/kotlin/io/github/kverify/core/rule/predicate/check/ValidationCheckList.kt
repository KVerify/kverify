package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * Composite [ValidationCheck] that executes a list of inner [checks] sequentially.
 */
@JvmInline
public value class ValidationCheckList<in T>(
    public val checks: List<ValidationCheck<T>>,
) : ValidationCheck<T> {
    override fun isValid(value: T): Boolean = checks.all { it.isValid(value) }
}

/**
 * Creates an empty [ValidationCheckList].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(): ValidationCheckList<T> =
    ValidationCheckList(
        checks = emptyList(),
    )

/**
 * Creates a [ValidationCheckList] containing a single [check].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(check: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = listOf(check),
    )

/**
 * Creates a [ValidationCheckList] from the provided [checks].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(vararg checks: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = checks.asList(),
    )

/**
 * Combines `this` [ValidationCheckList] with another [ValidationCheck], producing a new [ValidationCheckList]
 * that executes all checks in sequence.
 *
 * If the [other] check is itself a [ValidationCheckList], its checks are flattened into the resulting sequence.
 * The checks from `this` [ValidationCheckList] are executed first, followed by the checks from [other].
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheck<T>): ValidationCheckList<T> =
    when (other) {
        is ValidationCheckList<T> -> ValidationCheckList(checks + other.checks)
        else -> ValidationCheckList(checks + other)
    }

/**
 * Combines `this` [ValidationCheckList] with another [ValidationCheckList], producing a new [ValidationCheckList]
 * that executes all checks from both stacks in sequence.
 *
 * The checks from `this` [ValidationCheckList] are executed first, followed by the checks from [other].
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheckList<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks + other.checks,
    )
