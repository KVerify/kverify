package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionContainsAllCheck<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean = value.containsAll(elements)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllCheck(vararg elements: E): CollectionContainsAllCheck<E, C> =
    CollectionContainsAllCheck(
        elements = elements.toSet(),
    )
