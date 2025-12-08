package io.github.kverify.core.rule.predicate.check

import kotlin.jvm.JvmInline

/**
 * A composite [ValidationCheck] that wraps multiple checks with `AND` logic.
 *
 * A value is valid only if it passes all wrapped [checks].
 *
 * @property checks The list of checks to evaluate
 */
@JvmInline
public value class ValidationCheckList<in T>(
    public val checks: List<ValidationCheck<T>>,
) : ValidationCheck<T> {
    /**
     * Tests whether the given [value] passes all [checks].
     *
     * Stops execution at the first check that returns `false`.
     *
     * @return `true` if all checks pass, `false` otherwise
     */
    override fun isValid(value: T): Boolean = checks.all { it.isValid(value) }
}

/**
 * Creates an empty [ValidationCheckList].
 *
 * An empty check list validates all values as `true`.
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
 * Creates a [ValidationCheckList] containing the given [checks].
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheckList(vararg checks: ValidationCheck<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks = checks.asList(),
    )

/**
 * Combines `this` check list with another [ValidationCheck].
 *
 * If [other] is a [ValidationCheckList], its checks are flattened into the result.
 * Otherwise, [other] is appended as a single check.
 *
 * @param other The check to add
 * @return A new check list containing all checks
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheck<T>): ValidationCheckList<T> =
    when (other) {
        is ValidationCheckList<T> -> ValidationCheckList(checks + other.checks)
        else -> ValidationCheckList(checks + other)
    }

/**
 * Combines `this` check list with another [ValidationCheckList].
 *
 * The checks from both lists are flattened into a single list.
 *
 * @param other The check list to add
 * @return A new check list containing all checks from both lists
 */
public operator fun <T> ValidationCheckList<T>.plus(other: ValidationCheckList<T>): ValidationCheckList<T> =
    ValidationCheckList(
        checks + other.checks,
    )
