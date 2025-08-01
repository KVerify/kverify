@file:Suppress("NOTHING_TO_INLINE")

package io.github.kverify.violation.set.localization

private inline fun resolveName(
    name: String?,
    fallback: String,
): String = "'${name ?: fallback}'"

internal inline fun resolveCollectionName(name: String?): String = resolveName(name, "collection")

internal inline fun resolveComparableName(name: String?): String = resolveName(name, "comparable")

internal inline fun resolveStringName(name: String?): String = resolveName(name, "string")

internal inline fun <T> Iterable<T>.joinWithLimitAndBrackets(): String =
    this.joinToString(
        separator = ", ",
        prefix = "[",
        postfix = "]",
        limit = 5,
    )
