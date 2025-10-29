package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionContainsAllCheck<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean =
        when {
            elements.isEmpty() -> true
            value.isEmpty() -> false
            else -> value.containsAll(elements)
        }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllCheck(element: E): CollectionContainsAllCheck<E, C> =
    CollectionContainsAllCheck(
        elements = setOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllCheck(vararg elements: E): CollectionContainsAllCheck<E, C> =
    CollectionContainsAllCheck(
        elements = elements.asList(),
    )
