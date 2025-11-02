package io.github.kverify.check.set.collection

import io.github.kverify.core.rule.predicate.check.ValidationCheck
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
