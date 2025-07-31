package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck
import kotlin.jvm.JvmInline

@JvmInline
public value class CollectionContainsCheck<E, C : Collection<E>>(
    public val element: E,
) : ValidationCheck<C> {
    override fun isValid(value: C): Boolean = value.contains(element)
}
