package io.github.kverify.core.context

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

/**
 * A context for executing validation rules and handling violations.
 *
 * Implementations define how [violations][Violation] are handled when validation fails.
 *
 * @see CollectingValidationContext
 * @see FirstViolationValidationContext
 * @see ThrowingValidationContext
 */
@KverifyDsl
public fun interface ValidationContext {
    /**
     * Called when a validation [violation] occurs.
     *
     * Implementations define how to handle the [violation], such as collecting it,
     * throwing an exception, or stopping validation.
     *
     * @param violation The [Violation] that occurred
     */
    public fun onFailure(violation: Violation)

    /**
     * Validates `this` value using the given [rule] within `this` context.
     *
     * ### Example:
     * ```kt
     * verifyCollecting {
     *     user.email verifyWith isNotEmpty
     * }
     * ```
     *
     * @receiver The value to validate
     * @param rule The [Rule] to validate the value against
     * @return The original value
     */
    public infix fun <T> T.verifyWith(rule: Rule<T>): T {
        val context = this@ValidationContext
        val value = this@verifyWith

        rule.execute(
            context = context,
            value = value,
        )

        return value
    }

    /**
     * Validates `this` value using all [rules] sequentially within `this` context.
     *
     * ### Example:
     * ```kt
     * verifyCollecting {
     *     user.email verifyWith listOf(isNotEmpty, isValidEmail)
     * }
     * ```
     *
     * @receiver The value to validate
     * @param rules The iterable of [rules][Rule] to validate the value against
     * @return The original value
     */
    public infix fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val context = this@ValidationContext

        for (rule in rules) {
            rule.execute(
                context = context,
                value = value,
            )
        }

        return value
    }

    /**
     * Validates `this` value using all [rules] sequentially within `this` context.
     *
     * ### Example:
     * ```kt
     * verifyCollecting {
     *     user.email.verifyWith(isNotEmpty, isValidEmail)
     * }
     * ```
     *
     * @receiver The value to validate
     * @param rules The vararg of [rules][Rule] to validate the value against
     * @return The original value
     */
    public fun <T> T.verifyWith(vararg rules: Rule<T>): T =
        this.verifyWith(
            rules = rules.asIterable(),
        )
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return The [value] unchanged
 */
@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> ValidationContext.verify(value: T): T = value

/**
 * Validates [value] using the given [rule] within `this` context.
 *
 * ### Example:
 * ```kt
 * context.verify(user.email, isNotEmpty)
 * ```
 *
 * @param value The value to validate
 * @param rule The [Rule] to validate the value against
 * @return The original [value]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationContext.verify(
    value: T,
    rule: Rule<T>,
): T = value verifyWith rule

/**
 * Validates [value] using all [rules] sequentially within `this` context.
 *
 * ### Example:
 * ```kt
 * context.verify(user.email, isNotEmpty, isValidEmail)
 * ```
 *
 * @param value The value to validate
 * @param rules The vararg of [Rule]s to validate the value against
 * @return The original [value]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationContext.verify(
    value: T,
    vararg rules: Rule<T>,
): T = value.verifyWith(rules = rules)

/**
 * Validates [value] using all [rules] sequentially within `this` context.
 *
 * ### Example:
 * ```kt
 * context.verify(user.email, listOf(isNotEmpty, isValidEmail))
 * ```
 *
 * @param value The value to validate
 * @param rules The iterable of [Rule]s to validate the value against
 * @return The original [value]
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationContext.verify(
    value: T,
    rules: Iterable<Rule<T>>,
): T = value verifyWith rules

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return `this` value unchanged
 */
@Suppress("UnusedParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWith(context: ValidationContext): T = this

/**
 * Validates `this` value using the given [rule] within the given [context].
 *
 * ### Example:
 * ```kt
 * user.email.verifyWith(context, isNotEmpty)
 * ```
 *
 * @receiver The value to validate
 * @param context The [ValidationContext] to use
 * @param rule The [Rule] to validate `this` value against
 * @return The original value
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWith(
    context: ValidationContext,
    rule: Rule<T>,
): T {
    val value = this

    return with(context) { value verifyWith rule }
}

/**
 * Validates `this` value using all [rules] sequentially within the given [context].
 *
 * ### Example:
 * ```kt
 * user.email.verifyWith(context, isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param context The [ValidationContext] to use
 * @param rules The vararg of [Rule]s to validate `this` value against
 * @return The original value
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWith(
    context: ValidationContext,
    vararg rules: Rule<T>,
): T {
    val value = this

    return with(context) { value.verifyWith(rules = rules) }
}

/**
 * Validates `this` value using all [rules] sequentially within the given [context].
 *
 * ### Example:
 * ```kt
 * user.email.verifyWith(context, listOf(isNotEmpty, isValidEmail))
 * ```
 *
 * @receiver The value to validate
 * @param context The [ValidationContext] to use
 * @param rules The iterable of [Rule]s to validate `this` value against
 * @return The original value
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWith(
    context: ValidationContext,
    rules: Iterable<Rule<T>>,
): T {
    val value = this

    return with(context) { value.verifyWith(rules = rules) }
}

/**
 * Calls [onFailure] with a [violation][Violation] if [condition] is `true`.
 *
 * The [lazyViolation] lambda is only evaluated if [condition] is `true`.
 *
 * ### Example:
 * ```kt
 * verifyCollecting {
 *     failIf(user.email.isEmpty()) {
 *         violation("Email must not be empty")
 *     }
 * }
 * ```
 *
 * @param condition The condition to check
 * @param lazyViolation A lambda that creates the [Violation] if [condition] is `true`
 */
public inline fun ValidationContext.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()
        onFailure(violation)
    }
}

/**
 * Calls [onFailure] with a [violation][Violation] if [condition] is `false`.
 *
 * The [lazyViolation] lambda is only evaluated if [condition] is `false`.
 *
 * ### Example:
 * ```kt
 * verifyCollecting {
 *     failIfNot(user.email.isNotEmpty()) {
 *         violation("Email must not be empty")
 *     }
 * }
 * ```
 *
 * @param condition The condition to check
 * @param lazyViolation A lambda that creates the [Violation] if [condition] is `false`
 */
public inline fun ValidationContext.failIfNot(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit =
    failIf(
        condition = !condition,
        lazyViolation = lazyViolation,
    )
