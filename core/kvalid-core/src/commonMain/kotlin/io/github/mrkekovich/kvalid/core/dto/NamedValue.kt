package io.github.mrkekovich.kvalid.core.dto

/**
 * Represents a named value.
 *
 * @param T the type of the value
 * @property name the name of the value
 * @property value the actual value
 */
data class NamedValue<T>(
    val name: String,
    val value: T,
)
