@file:Suppress("NOTHING_TO_INLINE")

package io.github.kverify.violation.set.localization

private inline fun resolveName(name: String?): String =
    if (name != null) {
        "Field '$name'"
    } else {
        "Given value"
    }

internal inline fun resolveCollectionName(name: String?): String = resolveName(name)

internal inline fun resolveComparableName(name: String?): String = resolveName(name)

internal inline fun resolveStringName(name: String?): String = resolveName(name)

internal inline fun <T> Iterable<T>.joinWithLimitAndBrackets(): String =
    this.joinToString(
        separator = ", ",
        prefix = "[",
        postfix = "]",
        limit = 5,
    )
