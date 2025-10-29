package io.github.kverify.check.set.collection

import io.github.kverify.check.set.disjoint
import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionContainsNoneCheck<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean = disjoint(value, elements)
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneCheck(element: E): CollectionContainsNoneCheck<E, C> =
    CollectionContainsNoneCheck(
        elements = setOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneCheck(vararg elements: E): CollectionContainsNoneCheck<E, C> =
    CollectionContainsNoneCheck(
        elements = elements.asList(),
    )
