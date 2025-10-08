package io.github.kverify.named.model

import kotlin.reflect.KProperty

/**
 *  Represents a [value] paired with its [name].
 */
public data class NamedValue<out T>(
    val name: String,
    val value: T,
)

/**
 * Creates a [NamedValue] with `this` value and the given [name].
 */
public infix fun <T> T.withName(name: String): NamedValue<T> =
    NamedValue(
        name = name,
        value = this,
    )

/**
 * Creates a [NamedValue] with `this` string as [NamedValue.name] and the given [value].
 */
public infix fun <T> String.withValue(value: T): NamedValue<T> =
    NamedValue(
        name = this,
        value = value,
    )

/**
 * Creates a [NamedValue] using `this` property’s [KProperty.name] and the provided [value].
 */
public fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> =
    NamedValue(
        name = name,
        value = value,
    )

/**
 * Converts `this` pair into a [NamedValue], using [Pair.first] as the name and [Pair.second] as the value.
 */
public fun <T> Pair<String, T>.toNamed(): NamedValue<T> =
    NamedValue(
        name = first,
        value = second,
    )

/**
 * Invokes [block] if `this` [NamedValue.value] is not `null`.
 */
@Suppress("UNCHECKED_CAST")
public inline infix fun <T> NamedValue<T?>.ifValueNotNull(block: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) {
        block(this as NamedValue<T>)
    }

    return this
}

/**
 * Returns this [NamedValue] cast to a non-nullable type if `this` [NamedValue.value] is not `null`,
 * or `null` otherwise.
 */
@Suppress("UNCHECKED_CAST")
public fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? =
    if (value != null) {
        this as NamedValue<T>
    } else {
        null
    }
