package io.github.kverify.named.model

import io.github.kverify.named.exception.PropertyAccessException
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

// JVM-specific code. For Kotlin Multiplatform, see `NamedValue` class

/**
 * Returns a [NamedValue] representing `this` property and its current value.
 *
 * Attempts to read the property value via reflection. Temporarily sets the property's accessibility
 * to `true` to bypass access restrictions, then restores the original accessibility state.
 *
 * ### Example:
 * ```kt
 * data class User(val email: String)
 * val user = User("user@example.com")
 *
 * val namedEmail = user::email.toNamed()
 * // NamedValue(name = "email", value = "user@example.com")
 * ```
 *
 * @receiver The property to read and convert
 * @return A new [NamedValue] with the property's [name][KProperty.name] and its current value
 * @throws PropertyAccessException if the value cannot be accessed, such as when:
 * - Attempting to access a property without an instance (e.g., `Class::prop` instead of `instance::prop`)
 * - The property is inaccessible due to security restrictions
 */
public fun <T> KProperty<T>.toNamed(): NamedValue<T> =
    try {
        val originalIsAccessible = getter.isAccessible

        getter.isAccessible = true

        val namedValue =
            NamedValue(
                name = name,
                value = getter.call(),
            )

        getter.isAccessible = originalIsAccessible

        namedValue
    } catch (e: IllegalArgumentException) {
        throw PropertyAccessException(
            """
            Cannot access value of the property '$name' without an instance.
            Hint: Use instance::prop instead of Class::prop
            """.trimIndent(),
            e,
        )
    } catch (e: IllegalAccessException) {
        throw PropertyAccessException(
            "Illegal access to the property '$name'. Ensure the property is accessible and belongs to an instance.",
            e,
        )
    }
