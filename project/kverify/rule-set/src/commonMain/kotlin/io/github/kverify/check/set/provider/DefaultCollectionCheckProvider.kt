package io.github.kverify.check.set.provider

import io.github.kverify.core.rule.predicate.check.ValidationCheck

@Suppress("TooManyFunctions")
public class DefaultCollectionCheckProvider : CollectionCheckProvider {
    override fun <E, C : Collection<E>> contains(element: E): ValidationCheck<C> =
        ValidationCheck { value ->
            value.contains(element)
        }

    override fun <E, C : Collection<E>> containsAll(elements: Collection<E>): ValidationCheck<C> =
        ValidationCheck { value ->
            when {
                elements.isEmpty() -> true
                value.isEmpty() -> false
                else -> value.containsAll(elements)
            }
        }

    override fun <E, C : Collection<E>> containsNone(elements: Collection<E>): ValidationCheck<C> =
        ValidationCheck { value ->
            if (value.isEmpty() || elements.isEmpty()) {
                true
            } else {
                elements.none { it in value }
            }
        }

    override fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): ValidationCheck<C> =
        ValidationCheck { value ->
            if (value.isEmpty() || elements.isEmpty()) {
                true
            } else {
                elements.all { it in value }
            }
        }

    override fun <E, C : Collection<E>> notContains(element: E): ValidationCheck<C> =
        ValidationCheck { value ->
            !value.contains(element)
        }

    override fun notEmpty(): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.isNotEmpty()
        }

    @Suppress("ReturnCount")
    override fun distinct(): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            val size = value.size
            if (size <= 1) return@ValidationCheck true

            val set = HashSet<Any?>(size)
            for (element in value) {
                if (!set.add(element)) return@ValidationCheck false
            }

            true
        }

    override fun ofSize(size: Int): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size == size
        }

    override fun notOfSize(size: Int): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size != size
        }

    override fun minSize(minSize: Int): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size <= minSize
        }

    override fun maxSize(maxSize: Int): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size <= maxSize
        }

    override fun sizeBetween(sizeRange: IntRange): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size in sizeRange
        }

    override fun sizeNotBetween(sizeRange: IntRange): ValidationCheck<Collection<*>> =
        ValidationCheck { value ->
            value.size !in sizeRange
        }
}
