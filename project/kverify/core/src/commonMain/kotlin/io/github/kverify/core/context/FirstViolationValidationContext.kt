@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationContext] that records the first [Violation] and attempts to skip subsequent rules.
 *
 * This context optimizes validation by trying to skip rules when the [firstViolation] is already recorded.
 * However, it does not guarantee stopping the execution at the first violation - it will not interrupt
 * a rule that has already started executing.
 *
 * @see FirstViolationValidationContextImpl
 * @see verifyFirst
 * @see passes
 * @see notPasses
 */
public interface FirstViolationValidationContext : ValidationContext {
    /**
     * The first [Violation] encountered, or `null` if no violations occurred.
     */
    public val firstViolation: Violation?

    /**
     * Validates `this` value using the given [rule].
     *
     * Skips rule execution if [firstViolation] is already set.
     *
     * @receiver The value to validate
     * @param rule The [Rule] to validate against
     * @return The original value
     */
    override fun <T> T.verifyWith(rule: Rule<T>): T {
        val value = this
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        rule.execute(
            context = context,
            value = value,
        )

        return this
    }

    /**
     * Validates `this` value using all [rules] sequentially.
     *
     * Skips execution if [firstViolation] is already set before entering the loop.
     * Breaks the loop when [firstViolation] becomes non-null after executing a rule.
     *
     * @receiver The value to validate
     * @param rules The iterable of [rules][Rule] to validate against
     * @return The original value
     */
    override fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val context = this@FirstViolationValidationContext

        if (firstViolation != null) return value

        for (rule in rules) {
            rule.execute(
                context = context,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}

/**
 * Internal implementation of [FirstViolationValidationContext].
 *
 * Records only the first [Violation] via [onFailure], ignoring subsequent violations.
 */
@PublishedApi
internal class FirstViolationValidationContextImpl : FirstViolationValidationContext {
    /**
     * The first [Violation] recorded, or `null` if none occurred.
     */
    override var firstViolation: Violation? = null
        private set

    /**
     * ### The implementation is NOT thread safe.
     *
     * Records the [violation] only if [firstViolation] is `null`.
     *
     * Subsequent calls are ignored once the first violation is recorded.
     *
     * @param violation The [Violation] to record
     */
    override fun onFailure(violation: Violation) {
        if (firstViolation == null) firstViolation = violation
    }
}

/**
 * Executes the [init] block and returns the first [Violation] encountered, or `null`.
 *
 * Creates a [FirstViolationValidationContextImpl], applies the [init] block to it,
 * and extracts the [firstViolation][FirstViolationValidationContextImpl.firstViolation].
 *
 * @param init The validation block to execute
 * @return The first [Violation] encountered, or `null` if validation passed
 */
@PublishedApi
internal inline fun getFirstViolation(init: FirstViolationValidationContextImpl.() -> Unit): Violation? {
    contract {
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }

    return FirstViolationValidationContextImpl().apply(init).firstViolation
}

/**
 * Validates within a [FirstViolationValidationContext] and returns [ValidationResult].
 *
 * Records the first [Violation] via [onFailure][FirstViolationValidationContext.onFailure]
 * and attempts to skip subsequent rules when possible.
 * Returns [ValidationResult.Valid] if no violations occurred,
 * otherwise [ValidationResult.Invalid] with the first violation.
 *
 * ### Example:
 * ```kt
 * val result: ValidationResult = verifyFirst {
 *     user.name verifyWith isNotEmpty
 *     user.email verifyWith isValidEmail
 * }
 * ```
 *
 * @param block The validation block
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
public inline fun verifyFirst(crossinline block: FirstViolationValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val firstViolation = getFirstViolation(block)

    return if (firstViolation == null) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(firstViolation)
    }
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return [ValidationResult.Valid]
 */
@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithFirst(): ValidationResult.Valid = ValidationResult.Valid

/**
 * Validates `this` value using the given [rule] and returns the first violation if any.
 *
 * Records the first [Violation] via [onFailure][FirstViolationValidationContext.onFailure]
 * and attempts to skip subsequent rules when possible.
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithFirst isNotEmpty
 * ```
 *
 * @receiver The value to validate
 * @param rule The [Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithFirst(rule: Rule<T>): ValidationResult {
    val value = this

    return verifyFirst { value verifyWith rule }
}

/**
 * Validates `this` value using all [rules] and returns the first violation if any.
 *
 * Records the first [Violation] via [onFailure][FirstViolationValidationContext.onFailure]
 * and attempts to skip subsequent rules when possible.
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithFirst(isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param rules The vararg of [rules][Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithFirst(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return verifyFirst { value.verifyWith(rules = rules) }
}

/**
 * Validates `this` value using all [rules] and returns the first violation if any.
 *
 * Records the first [Violation] via [onFailure][FirstViolationValidationContext.onFailure]
 * and attempts to skip subsequent rules when possible.
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithFirst listOf(isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param rules The iterable of [rules][Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithFirst(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return verifyFirst { value verifyWith rules }
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `true`
 */
@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.passes(): Boolean = true

/**
 * Checks whether `this` value passes the given [rule].
 *
 * Validates using [getFirstViolation] and returns `true` if no violation occurred.
 *
 * ### Example:
 * ```kt
 * if (user.email passes isNotEmpty) {
 *     // email is not empty
 * }
 * ```
 *
 * @receiver The value to check
 * @param rule The [Rule] to validate against
 * @return `true` if validation passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passes(rule: Rule<T>): Boolean {
    val value = this

    return getFirstViolation { value verifyWith rule } == null
}

/**
 * Checks whether `this` value passes all [rules].
 *
 * Validates using [getFirstViolation] and returns `true` if no violation occurred.
 *
 * ### Example:
 * ```kt
 * if (user.email.passes(isNotEmpty, isValidEmail)) {
 *     // email is valid
 * }
 * ```
 *
 * @receiver The value to check
 * @param rules The vararg of [rules][Rule] to validate against
 * @return `true` if all validations passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.passes(vararg rules: Rule<T>): Boolean {
    val value = this

    return getFirstViolation { value.verifyWith(rules = rules) } == null
}

/**
 * Checks whether `this` value passes all [rules].
 *
 * Validates using [getFirstViolation] and returns `true` if no violation occurred.
 *
 * ### Example:
 * ```kt
 * if (user.email passes listOf(isNotEmpty, isValidEmail)) {
 *     // email is valid
 * }
 * ```
 *
 * @receiver The value to check
 * @param rules The iterable of [rules][Rule] to validate against
 * @return `true` if all validations passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passes(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return getFirstViolation { value verifyWith rules } == null
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `false`
 */
@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notPasses(): Boolean = false

/**
 * Checks whether `this` value fails the given [rule].
 *
 * Returns the negation of [passes].
 *
 * ### Example:
 * ```kt
 * if (user.email notPasses isNotEmpty) {
 *     // email is empty
 * }
 * ```
 *
 * @receiver The value to check
 * @param rule The [Rule] to validate against
 * @return `true` if validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPasses(rule: Rule<T>): Boolean =
    !passes(
        rule = rule,
    )

/**
 * Checks whether `this` value fails any of the [rules].
 *
 * Returns the negation of [passes].
 *
 * ### Example:
 * ```kt
 * if (user.email.notPasses(isNotEmpty, isValidEmail)) {
 *     // email is invalid
 * }
 * ```
 *
 * @receiver The value to check
 * @param rules The vararg of [rules][Rule] to validate against
 * @return `true` if any validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.notPasses(vararg rules: Rule<T>): Boolean =
    !passes(
        rules = rules,
    )

/**
 * Checks whether `this` value fails any of the [rules].
 *
 * Returns the negation of [passes].
 *
 * ### Example:
 * ```kt
 * if (user.email notPasses listOf(isNotEmpty, isValidEmail)) {
 *     // email is invalid
 * }
 * ```
 *
 * @receiver The value to check
 * @param rules The iterable of [rules][Rule] to validate against
 * @return `true` if any validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPasses(rules: Iterable<Rule<T>>): Boolean =
    !passes(
        rules = rules,
    )
