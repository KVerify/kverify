package io.github.kverify.named.model

import kotlin.reflect.KProperty

/**
 * Represents a [value] paired with its [name].
 *
 * A lightweight wrapper around a `Pair<String, T>` that provides semantic meaning
 * for values that need to be identified by name, such as in validation contexts.
 *
 * @param pair The underlying pair containing the [name] and [value]
 */
public value class NamedValue<out T>(
    public val pair: Pair<String, T>,
) {
    public constructor(
        name: String,
        value: T,
    ) : this(name to value)

    /**
     * The name associated with the [value].
     */
    public inline val name: String get() = pair.first

    /**
     * The value associated with the [name].
     */
    public inline val value: T get() = pair.second

    /**
     * @return The [name] component for destructuring.
     */
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun component1(): String = name

    /**
     * @return The [value] component for destructuring.
     */
    @Suppress("NOTHING_TO_INLINE")
    public inline operator fun component2(): T = value
}

/**
 * Creates a [NamedValue] with `this` value and the given [name].
 *
 * ### Example:
 * ```kt
 * val email = "user@example.com"
 * val namedEmail = email.withName("email")
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The value to be named
 * @param name The name to associate with `this` value
 * @return A new [NamedValue] with the given [name] and `this` value
 */
public infix fun <T> T.withName(name: String): NamedValue<T> =
    NamedValue(
        name = name,
        value = this,
    )

/**
 * Creates a [NamedValue] with `this` string as [NamedValue.name] and the given [value].
 *
 * ### Example:
 * ```kt
 * val namedEmail = "email".withValue("user@example.com")
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The name to associate with the [value]
 * @param value The value to be named
 * @return A new [NamedValue] with `this` string as the name and the given [value]
 */
public infix fun <T> String.withValue(value: T): NamedValue<T> =
    NamedValue(
        name = this,
        value = value,
    )

/**
 * Creates a [NamedValue] using `this` property's [name][KProperty.name] and the provided [value].
 *
 * Useful for automatically deriving the name from a property reference.
 *
 * ### Example:
 * ```kt
 * data class User(val email: String)
 *
 * val namedEmail = User::email.toNamed("user@example.com")
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The property to extract the name from
 * @param value The value to associate with the property name
 * @return A new [NamedValue] with the property's name and the given [value]
 */
public fun <T> KProperty<T>.toNamed(value: T): NamedValue<T> =
    NamedValue(
        name = name,
        value = value,
    )

/**
 * Converts `this` pair into a [NamedValue], using [Pair.first] as the name and [Pair.second] as the value.
 *
 * ### Example:
 * ```kt
 * val pair = "email" to "user@example.com"
 * val namedEmail = pair.toNamed()
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The pair to convert
 * @return A new [NamedValue] wrapping `this` pair
 */
public fun <T> Pair<String, T>.toNamed(): NamedValue<T> = NamedValue(this)

/**
 * Invokes [block] if `this` [NamedValue.value] is not `null`.
 *
 * Useful for conditionally performing operations on non-null values while maintaining fluent chaining.
 *
 * ### Example:
 * ```kt
 * val nullableEmail: NamedValue<String?> = "email".withValue(null)
 * val email: NamedValue<String?> = "email".withValue("user@example.com")
 *
 * nullableEmail.ifValueNotNull { namedValue ->
 *     println("Email: ${namedValue.value}")
 * } // Does not execute
 *
 * email.ifValueNotNull { namedValue ->
 *     println("Email: ${namedValue.value}")
 * } // Prints: "Email: user@example.com"
 * ```
 *
 * @receiver The nullable [NamedValue] to check
 * @param block The block to execute with the non-null [NamedValue]
 * @return `this` [NamedValue]
 */
@Suppress("UNCHECKED_CAST")
public inline infix fun <T> NamedValue<T?>.ifValueNotNull(block: (NamedValue<T>) -> Unit): NamedValue<T?> {
    if (value != null) {
        block(this as NamedValue<T>)
    }

    return this
}

/**
 * Returns `this` [NamedValue] cast to a non-nullable type if `this` [NamedValue.value] is not `null`,
 * or `null` otherwise.
 *
 * ### Example:
 * ```kt
 * val nullableEmail: NamedValue<String?> = "email".withValue(null)
 * val email: NamedValue<String?> = "email".withValue("user@example.com")
 *
 * nullableEmail.unwrapOrNull() // null
 * email.unwrapOrNull() // NamedValue<String>(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The nullable [NamedValue] to unwrap
 * @return `this` [NamedValue] cast to non-nullable type if [value][NamedValue.value] is not `null`, or `null` otherwise
 */
@Suppress("UNCHECKED_CAST")
public fun <T> NamedValue<T?>.unwrapOrNull(): NamedValue<T>? =
    if (value != null) {
        this as NamedValue<T>
    } else {
        null
    }

/**
 * Returns a new [NamedValue] with the same [NamedValue.name] and the result of [transform].
 *
 * ### Example:
 * ```kt
 * val email = "email".withValue("  user@example.com  ")
 * val trimmedEmail = email.map { it.trim() }
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The [NamedValue] to transform
 * @param transform The transformation to apply to the [value][NamedValue.value]
 * @return A new [NamedValue] with the same [name][NamedValue.name] and transformed value
 */
public inline fun <T> NamedValue<T>.map(transform: (T) -> T): NamedValue<T> =
    NamedValue(
        name = name,
        value = transform(value),
    )
