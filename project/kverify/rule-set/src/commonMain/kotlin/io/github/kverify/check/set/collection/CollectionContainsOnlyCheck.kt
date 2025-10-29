package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionContainsOnlyCheck<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean =
        if (value.isEmpty() || elements.isEmpty()) {
            true
        } else {
            elements.all { it in value }
        }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyCheck(element: E): CollectionContainsOnlyCheck<E, C> =
    CollectionContainsOnlyCheck(
        elements = setOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyCheck(vararg elements: E): CollectionContainsOnlyCheck<E, C> =
    CollectionContainsOnlyCheck(
        elements = elements.asList(),
    )
