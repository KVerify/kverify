package io.github.kverify.core.model

import kotlin.reflect.KProperty

/**
 * Represents a [value] with an associated [name].
 *
 * @property name The name of the value.
 * @property value The actual value of type [T].
 */
data class NamedValue<out T>(
    val name: String,
    val value: T,
)

/**
 * Creates a tuple of type [NamedValue] from [name] and this value.
 */
infix fun <T> T.withName(name: String): NamedValue<T> = NamedValue(name, this)

/**
 * Creates a tuple of type [NamedValue] from this name and [value].
 */
infix fun <T> String.withValue(value: T): NamedValue<T> = NamedValue(this, value)

/**
 * Creates a tuple of type [NamedValue] from this [KProperty.name] and [value].
 */
fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> = NamedValue(name, value)

/**
 * Converts this [Pair] into [NamedValue].
 */
fun <T> Pair<String, T>.toNamed(): NamedValue<T> = NamedValue(first, second)

/**
 * Executes [action] if [NamedValue.value] is not `null`.
 */
@Suppress("UNCHECKED_CAST")
inline infix fun <T> NamedValue<T?>.ifValueNotNull(action: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) action(this as NamedValue<T>)
    return this
}

/**
 * @return this [NamedValue] if [NamedValue.value] is not `null`, otherwise returns `null`.
 */
@Suppress("UNCHECKED_CAST")
fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? =
    if (value != null) {
        this as NamedValue<T>
    } else {
        null
    }
