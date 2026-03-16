package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement

/**
 * A value bound to a [ValidationScope], ready for validation rules to be applied.
 *
 * [Verification] is the primary surface for chaining validation rules.
 * It is created by [ValidationScope.verify] and consumed by rule extension functions
 * defined on `Verification<T>` or its supertypes.
 *
 * The [scope] carries the active [io.github.kverify.core.context.ValidationContext],
 * so rules can attach path and other metadata to any violations they produce.
 *
 * @param value The value being validated.
 * @param scope The scope in which rules on this verification are enforced.
 */
public class Verification<out T>(
    public val value: T,
    public val scope: ValidationScope,
)

/**
 * Returns this [Verification] narrowed to a non-null type if [Verification.value] is not `null`, or `null` otherwise.
 *
 * Allows null-safe chaining with any rule defined on `Verification<SomeType>`:
 * ```kotlin
 * verify(someTypeNullable)
 *     .takeIfNotNull()
 *     ?.myRule()
 * ```
 */
public fun <T : Any> Verification<T?>.takeIfNotNull(): Verification<T>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as Verification<T>
    } else {
        null
    }

/**
 * Validates each element of the iterable, automatically appending each element's index to the validation path.
 *
 * Returns `this` so further rules can be chained on the iterable itself.
 */
public inline fun <T, I : Iterable<T>> Verification<I>.each(block: ValidationScope.(T) -> Unit): Verification<I> =
    each { _, element ->
        block(element)
    }

/**
 * Validates each element of the iterable, automatically appending each element's index to the validation path.
 *
 * Exposes both the index and the element to the [block].
 *
 * Returns `this` so further rules can be chained on the iterable itself.
 */
public inline fun <T, I : Iterable<T>> Verification<I>.each(block: ValidationScope.(Int, T) -> Unit): Verification<I> =
    apply {
        for ((idx, element) in value.withIndex()) {
            val indexedScope = scope + IndexPathElement(idx)

            indexedScope.block(idx, element)
        }
    }
