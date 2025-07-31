package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck

public object CollectionDistinctCheck : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.isEmpty() || value.toSet().size == value.size
}
