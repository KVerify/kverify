@file:Suppress("TooManyFunctions")

package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationContext] that collects all violations into a [mutable collection][violationsStorage].
 *
 * @param violationsStorage The mutable collection to store violations in
 * @see verifyCollecting
 * @see verifyCollectingTo
 * @see verifyCollectingUsing
 */
public open class CollectingValidationContext(
    protected val violationsStorage: MutableCollection<Violation>,
) : ValidationContext {
    /**
     * Adds the given [violation] to the [violationsStorage].
     */
    override fun onFailure(violation: Violation) {
        violationsStorage.add(violation)
    }

    /**
     * Builds a [ValidationResult] from the collected violations.
     *
     * @return [ValidationResult.Valid] if no violations were collected, otherwise [ValidationResult.Invalid]
     */
    public open fun build(): ValidationResult =
        ValidationResult(
            violationsStorage.toList(),
        )
}

// ============================================================
// Validate using AggregatingValidationContext with ArrayList as violationStorage
// ============================================================

/**
 * Validates within a [CollectingValidationContext] and returns [ValidationResult].
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into a new [ArrayList] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val result: ValidationResult = verifyCollecting {
 *     user.name verifyWith isNotEmpty
 *     user.email verifyWith isValidEmail
 * }
 * ```
 *
 * @param block The validation block
 * @return [ValidationResult] containing all collected violations
 */
@OptIn(ExperimentalContracts::class)
public inline fun verifyCollecting(block: CollectingValidationContext.() -> Unit): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyCollectingTo(ArrayList(), block)
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return [ValidationResult.Valid]
 */
@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollecting(): ValidationResult.Valid = ValidationResult.Valid

/**
 * Validates `this` value using the given [rule] and collects all violations.
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into a new [ArrayList] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithCollecting isNotEmpty
 * ```
 *
 * @receiver The value to validate
 * @param rule The [Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithCollecting(rule: Rule<T>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rule = rule,
    )

/**
 * Validates `this` value using all [rules] and collects all violations.
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into a new [ArrayList] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithCollecting(isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param rules The vararg of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollecting(vararg rules: Rule<T>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rules = rules,
    )

/**
 * Validates `this` value using all [rules] and collects all violations.
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into a new [ArrayList] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val result = user.email verifyWithCollecting listOf(isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param rules The iterable of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline infix fun <T> T.verifyWithCollecting(rules: Iterable<Rule<T>>): ValidationResult =
    this.verifyWithCollectingTo(
        destination = ArrayList(),
        rules = rules,
    )

// ============================================================
// Validate using AggregatingValidationContext with provided destination as violationStorage
// ============================================================

/**
 * Validates within a [CollectingValidationContext] and collects violations to [destination].
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into the [destination] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val violations = mutableListOf<Violation>()
 * val result = verifyCollectingTo(violations) {
 *     user.name verifyWith isNotEmpty
 *     user.email verifyWith isValidEmail
 * }
 * ```
 *
 * @param destination The mutable collection to store violations in
 * @param block The validation block
 * @return [ValidationResult] containing all collected violations
 */
@OptIn(ExperimentalContracts::class)
public inline fun verifyCollectingTo(
    destination: MutableCollection<Violation>,
    block: CollectingValidationContext.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return verifyCollectingUsing(
        context = CollectingValidationContext(destination),
        block,
    )
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return [ValidationResult.Valid]
 */
@Suppress("UnusedParameter", "UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(destination: MutableCollection<Violation>): ValidationResult.Valid = ValidationResult.Valid

/**
 * Validates `this` value using the given [rule] and collects violations to [destination].
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into the [destination] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val violations = mutableListOf<Violation>()
 * val result = user.email.verifyWithCollectingTo(violations, isNotEmpty)
 * ```
 *
 * @receiver The value to validate
 * @param destination The mutable collection to store violations in
 * @param rule The [Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    rule: Rule<T>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rule = rule,
    )

/**
 * Validates `this` value using all [rules] and collects violations to [destination].
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into the [destination] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val violations = mutableListOf<Violation>()
 * val result = user.email.verifyWithCollectingTo(violations, isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param destination The mutable collection to store violations in
 * @param rules The vararg of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    vararg rules: Rule<T>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rules = rules,
    )

/**
 * Validates `this` value using all [rules] and collects violations to [destination].
 *
 * Collects all [violations][Violation] reported via [onFailure][CollectingValidationContext.onFailure]
 * into the [destination] and [builds][CollectingValidationContext.build] a [ValidationResult] from it.
 *
 * ### Example:
 * ```kt
 * val violations = mutableListOf<Violation>()
 * val result = user.email.verifyWithCollectingTo(violations, listOf(isNotEmpty, isValidEmail))
 * ```
 *
 * @receiver The value to validate
 * @param destination The mutable collection to store violations in
 * @param rules The iterable of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> T.verifyWithCollectingTo(
    destination: MutableCollection<Violation>,
    rules: Iterable<Rule<T>>,
): ValidationResult =
    this.verifyWithCollectingUsing(
        context = CollectingValidationContext(destination),
        rules = rules,
    )

// ============================================================
// Validate using provided context
// ============================================================

/**
 * Validates within a custom [CollectingValidationContext] implementation.
 *
 * ### Example:
 * ```kt
 * class CustomContext : CollectingValidationContext(ArrayList())
 *
 * val result = verifyCollectingUsing(CustomContext()) {
 *     user.name verifyWith isNotEmpty
 *     user.email verifyWith isValidEmail
 * }
 * ```
 *
 * @param context The custom [CollectingValidationContext] to use
 * @param block The validation block
 * @return [ValidationResult] containing all collected violations
 */
@OptIn(ExperimentalContracts::class)
public inline fun <C : CollectingValidationContext> verifyCollectingUsing(
    context: C,
    block: C.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return context.apply(block).build()
}

/**
 * This overload exists to optimize the no-argument case for `vararg` overload
 *
 * @return [ValidationResult] from [context]
 */
@Suppress("UnusedReceiverParameter", "NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(context: C): ValidationResult = context.build()

/**
 * Validates `this` value using the given [rule] within a custom [context].
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithCollectingUsing(CustomContext(), isNotEmpty)
 * ```
 *
 * @receiver The value to validate
 * @param context The custom [CollectingValidationContext] to use
 * @param rule The [Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    rule: Rule<T>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) { value verifyWith rule }
}

/**
 * Validates `this` value using all [rules] within a custom [context].
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithCollectingUsing(CustomContext(), isNotEmpty, isValidEmail)
 * ```
 *
 * @receiver The value to validate
 * @param context The custom [CollectingValidationContext] to use
 * @param rules The vararg of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    vararg rules: Rule<T>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) {
        value.verifyWith(rules = rules)
    }
}

/**
 * Validates `this` value using all [rules] within a custom [context].
 *
 * ### Example:
 * ```kt
 * val result = user.email.verifyWithCollectingUsing(CustomContext(), listOf(isNotEmpty, isValidEmail))
 * ```
 *
 * @receiver The value to validate
 * @param context The custom [CollectingValidationContext] to use
 * @param rules The iterable of [rules][Rule] to validate against
 * @return [ValidationResult] containing all collected violations
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T, C : CollectingValidationContext> T.verifyWithCollectingUsing(
    context: C,
    rules: Iterable<Rule<T>>,
): ValidationResult {
    val value = this

    return verifyCollectingUsing(context) { value verifyWith rules }
}
