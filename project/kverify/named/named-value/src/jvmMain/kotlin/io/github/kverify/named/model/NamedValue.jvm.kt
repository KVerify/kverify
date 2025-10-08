package io.github.kverify.named.model

import io.github.kverify.named.exception.PropertyAccessException
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

// JVM-specific code. For Kotlin Multiplatform, see `NamedValue` class

/**
 * Returns a [NamedValue] representing `this` property and its current value.
 *
 * Attempts to read the property value via reflection.
 *
 * @throws [PropertyAccessException] if the value cannot be accessed.
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
