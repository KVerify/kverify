@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.exception.ThrowingValidationContextException
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.js.JsName

/**
 * A [ValidationContext] where [onFailure] never returns normally.
 *
 * Implementations must throw an exception or terminate execution when [onFailure] is called.
 * The standard implementation [ThrowingValidationObject] throws [ThrowingValidationContextException].
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @see ThrowingValidationObject
 * @see verifyThrowing
 * @see verifyFailFast
 * @see passesFailFast
 */
public fun interface ThrowingValidationContext : ValidationContext {
    /**
     * Handles a validation [violation] by never returning normally.
     *
     * Implementations must throw an exception or terminate execution.
     *
     * @param violation The [Violation] that occurred
     */
    override fun onFailure(violation: Violation): Nothing
}

/**
 * @return The singleton [ThrowingValidationObject]
 */
@Suppress("NOTHING_TO_INLINE")
@JsName("ThrowingValidationContextFactory")
public inline fun ThrowingValidationContext(): ThrowingValidationContext = ThrowingValidationObject

/**
 * Throws [ThrowingValidationContextException] if [condition] is `true`.
 *
 * The [lazyViolation] lambda is only evaluated if [condition] is `true`.
 * If [condition] is `false`, the function returns normally, ensuring smart cast.
 *
 * ### Example:
 * ```kt
 * val anything: Any? = /*...*/
 *
 * ThrowingValidationContext().failIf(anything !is String) {
 *     violation("Value must be a string")
 * }
 *
 * val string: String = anything // smart cast to String
 * ```
 *
 * @param condition The condition to check
 * @param lazyViolation A lambda that creates the [Violation] if [condition] is `true`
 * @throws ThrowingValidationContextException if [condition] is `true`
 * @see contract
 */
@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies !condition
    }

    if (condition) onFailure(lazyViolation())
}

/**
 * Throws [ThrowingValidationContextException] if [condition] is `false`.
 *
 * The [lazyViolation] lambda is only evaluated if [condition] is `false`.
 * If [condition] is `true`, the function returns normally, ensuring smart cast.
 *
 * ### Example:
 * ```kt
 * val anything: Any? = /*...*/
 *
 * ThrowingValidationContext().failIfNot(anything is String) {
 *     violation("Value must be a string")
 * }
 *
 * val string: String = anything // smart cast to String
 * ```
 *
 * @param condition The condition to check
 * @param lazyViolation A lambda that creates the [Violation] if [condition] is `false`
 * @throws ThrowingValidationContextException if [condition] is `false`
 */
@OptIn(ExperimentalContracts::class)
public inline fun ThrowingValidationContext.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        returns() implies condition
    }

    failIf(!condition, lazyViolation)
}

/**
 * Singleton implementation of [ThrowingValidationContext].
 *
 * Throws [ThrowingValidationContextException] when [onFailure] is called.
 */
@PublishedApi
internal object ThrowingValidationObject : ThrowingValidationContext {
    /**
     * Throws [ThrowingValidationContextException] with the given [violation].
     *
     * @param violation The [Violation] that occurred
     * @throws ThrowingValidationContextException always
     */
    override fun onFailure(violation: Violation): Nothing =
        throw ThrowingValidationContextException(
            violation = violation,
        )
}

// ============================================================
// Throw on failure
// ============================================================

/**
 * Validates within a [ThrowingValidationObject], throwing on the first [Violation].
 *
 * Executes the [block] with [ThrowingValidationObject].
 * If a violation occurs, [ThrowingValidationContextException] is thrown.
 * The [block] is guaranteed to run exactly once, so smart casts work outside the block.
 *
 * ### Example:
 * ```kt
 * val anything: Any? = /*...*/
 *
 * val user = verifyThrowing {
 *     failIf(anything !is User) {
 *         violation("Value must be a User")
 *     }
 *     anything.name verifyWith isNotEmpty
 *     anything
 * }
 *
 * val typedUser: User = anything // smart cast to User works here
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @param block The validation block
 * @return The result of [block] if validation passed
 * @throws ThrowingValidationContextException if any validation fails
 * @see contract
 */
@OptIn(ExperimentalContracts::class)
public inline fun <T> verifyThrowing(block: ThrowingValidationContext.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationObject.run(block)
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `this` value unchanged
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithThrowing(): T = this

/**
 * Validates `this` value using the given [rule], throwing on violation.
 *
 * ### Example:
 * ```kt
 * val email = user.email verifyWithThrowing isNotEmpty
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rule The [Rule] to validate against
 * @return `this` value if validation passed
 * @throws ThrowingValidationContextException if validation fails
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithThrowing(rule: Rule<T>): T {
    val value = this

    return verifyThrowing { value verifyWith rule }
}

/**
 * Validates `this` value using all [rules], throwing on the first violation.
 *
 * ### Example:
 * ```kt
 * val email = user.email.verifyWithThrowing(isNotEmpty, isValidEmail)
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rules The vararg of [rules][Rule] to validate against
 * @return `this` value if all validations passed
 * @throws ThrowingValidationContextException if any validation fails
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithThrowing(vararg rules: Rule<T>): T {
    val value = this

    return verifyThrowing { value.verifyWith(rules = rules) }
}

/**
 * Validates `this` value using all [rules], throwing on the first violation.
 *
 * ### Example:
 * ```kt
 * val email = user.email verifyWithThrowing listOf(isNotEmpty, isValidEmail)
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rules The iterable of [rules][Rule] to validate against
 * @return `this` value if all validations passed
 * @throws ThrowingValidationContextException if any validation fails
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithThrowing(rules: Iterable<Rule<T>>): T {
    val value = this

    return verifyThrowing { value.verifyWith(rules = rules) }
}

// ============================================================
// Throw and catch on failure
// ============================================================

/**
 * Executes [block] and catches [ThrowingValidationContextException].
 *
 * Runs the [block] with [ThrowingValidationObject]. If [ThrowingValidationContextException]
 * is thrown, invokes [onFailure] with the exception and returns its result.
 *
 * @param block The validation block to execute
 * @param onFailure Handler for [ThrowingValidationContextException]
 * @return Result of [block] if no exception, or result of [onFailure] if exception caught
 */
@PublishedApi
internal inline fun <T> runFailFast(
    block: ThrowingValidationContext.() -> T,
    onFailure: (ThrowingValidationContextException) -> T,
): T =
    try {
        block(ThrowingValidationObject)
    } catch (e: ThrowingValidationContextException) {
        onFailure(e)
    }

/**
 * Validates within a [ThrowingValidationContext] and returns [ValidationResult].
 *
 * Catches [ThrowingValidationContextException] and converts it to [ValidationResult.Invalid].
 * Returns [ValidationResult.Valid] if no violations occurred.
 *
 * **Note:** Smart casts work only inside the [block], not outside it.
 *
 * ### Example:
 * ```kt
 * val anything: Any? = /*...*/
 *
 * val result: ValidationResult = verifyFailFast {
 *     failIf(anything !is User) {
 *         violation("Value must be a User")
 *     }
 *     val user: User = anything // smart cast works here
 * }
 *
 * // anything is not smart casted to User here
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @param block The validation block
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
public inline fun verifyFailFast(block: ThrowingValidationContext.() -> Unit): ValidationResult =
    runFailFast({
        ThrowingValidationObject.block()
        ValidationResult.Valid
    }) {
        ValidationResult.Invalid(it.violation)
    }

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return [ValidationResult.Valid]
 */
@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithFailFast(): ValidationResult = ValidationResult.Valid

/**
 * Validates `this` value using the given [rule] and returns [ValidationResult].
 *
 * Catches [ThrowingValidationContextException] and converts it to [ValidationResult.Invalid].
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithFailFast isNotEmpty
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rule The [Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithFailFast(rule: Rule<T>): ValidationResult {
    val value = this

    return verifyFailFast { value verifyWith rule }
}

/**
 * Validates `this` value using all [rules] and returns [ValidationResult].
 *
 * Catches [ThrowingValidationContextException] and converts it to [ValidationResult.Invalid].
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithFailFast(isNotEmpty, isValidEmail)
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rules The vararg of [rules][Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithFailFast(vararg rules: Rule<T>): ValidationResult {
    val value = this

    return verifyFailFast { value.verifyWith(rules = rules) }
}

/**
 * Validates `this` value using all [rules] and returns [ValidationResult].
 *
 * Catches [ThrowingValidationContextException] and converts it to [ValidationResult.Invalid].
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithFailFast listOf(isNotEmpty, isValidEmail)
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to validate
 * @param rules The iterable of [rules][Rule] to validate against
 * @return [ValidationResult.Valid] or [ValidationResult.Invalid] with the first violation
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithFailFast(rules: Iterable<Rule<T>>): ValidationResult {
    val value = this

    return verifyFailFast { value.verifyWith(rules = rules) }
}

// ============================================================
// Satisfies
// ============================================================

/**
 * Executes [block] and returns `true` if [ThrowingValidationContextException] was thrown.
 *
 * Runs the [block] with [ThrowingValidationObject]. Returns `false` if no exception,
 * or `true` if [ThrowingValidationContextException] was caught.
 *
 * @param block The validation block to execute
 * @return `true` if exception was thrown, `false` otherwise
 */
@PublishedApi
internal inline fun failFastThrows(block: ThrowingValidationContext.() -> Unit): Boolean =
    runFailFast({
        ThrowingValidationObject.block()
        false
    }) {
        true
    }

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `true`
 */
@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.passesFailFast(): Boolean = true

/**
 * Checks whether `this` value passes the given [rule] using fail-fast validation.
 *
 * Uses [ThrowingValidationContext] to validate and returns `true` if no exception was thrown.
 *
 * ### Example:
 * ```kt
 * if (user.email passesFailFast isNotEmpty) {
 *     // email is not empty
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rule The [Rule] to validate against
 * @return `true` if validation passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passesFailFast(rule: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rule }
}

/**
 * Checks whether `this` value passes all [rules] using fail-fast validation.
 *
 * Uses [ThrowingValidationContext] to validate and returns `true` if no exception was thrown.
 *
 * ### Example:
 * ```kt
 * if (user.email.passesFailFast(isNotEmpty, isValidEmail)) {
 *     // email is valid
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rules The vararg of [rules][Rule] to validate against
 * @return `true` if all validations passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.passesFailFast(vararg rules: Rule<T>): Boolean {
    val value = this

    return !failFastThrows { value.verifyWith(rules = rules) }
}

/**
 * Checks whether `this` value passes all [rules] using fail-fast validation.
 *
 * Uses [ThrowingValidationContext] to validate and returns `true` if no exception was thrown.
 *
 * ### Example:
 * ```kt
 * if (user.email passesFailFast listOf(isNotEmpty, isValidEmail)) {
 *     // email is valid
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rules The iterable of [rules][Rule] to validate against
 * @return `true` if all validations passed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.passesFailFast(rules: Iterable<Rule<T>>): Boolean {
    val value = this

    return !failFastThrows { value verifyWith rules }
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `false`
 */
@Suppress("UnusedReceiverParameter", "FunctionOnlyReturningConstant", "NOTHING_TO_INLINE")
public inline fun <T> T.notPassesFailFast(): Boolean = false

/**
 * Checks whether `this` value fails the given [rule] using fail-fast validation.
 *
 * Returns the negation of [passesFailFast].
 *
 * ### Example:
 * ```kt
 * if (user.email notPassesFailFast isNotEmpty) {
 *     // email is empty
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rule The [Rule] to validate against
 * @return `true` if validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPassesFailFast(rule: Rule<T>): Boolean =
    !passesFailFast(
        rule = rule,
    )

/**
 * Checks whether `this` value fails any of the [rules] using fail-fast validation.
 *
 * Returns the negation of [passesFailFast].
 *
 * ### Example:
 * ```kt
 * if (user.email.notPassesFailFast(isNotEmpty, isValidEmail)) {
 *     // email is invalid
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rules The vararg of [rules][Rule] to validate against
 * @return `true` if any validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.notPassesFailFast(vararg rules: Rule<T>): Boolean =
    !passesFailFast(
        rules = rules,
    )

/**
 * Checks whether `this` value fails any of the [rules] using fail-fast validation.
 *
 * Returns the negation of [passesFailFast].
 *
 * ### Example:
 * ```kt
 * if (user.email notPassesFailFast listOf(isNotEmpty, isValidEmail)) {
 *     // email is invalid
 * }
 * ```
 *
 * ### Warning:
 * **Rules that catch [Exception] or [Throwable] may suppress [onFailure] calls,**
 * **breaking the expected fail-fast behavior. Ensure rules do not catch these exception types.**
 *
 * @receiver The value to check
 * @param rules The iterable of [rules][Rule] to validate against
 * @return `true` if any validation failed, `false` otherwise
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.notPassesFailFast(rules: Iterable<Rule<T>>): Boolean =
    !passesFailFast(
        rules = rules,
    )
