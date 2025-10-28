package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

private const val HASH_SET_ALLOCATION_THRESHOLD = 64

@JvmInline
public value class CollectionContainsAllCheck<E, C : Collection<E>>(
    public val elements: Collection<E>,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean =
        when {
            elements.isEmpty() -> true
            value.isEmpty() -> false
            value is Set<*> -> value.containsAll(elements)
            else -> {
                val n = value.size
                val m = elements.size

                val lookup =
                    if (m > 1 && n > HASH_SET_ALLOCATION_THRESHOLD / m) {
                        value.toSet()
                    } else {
                        value
                    }

                elements.all { it in lookup }
            }
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
        elements = setOf(elements = elements),
    )
