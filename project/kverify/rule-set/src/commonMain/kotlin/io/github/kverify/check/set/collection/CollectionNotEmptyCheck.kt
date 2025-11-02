package io.github.kverify.check.set.collection

import io.github.kverify.core.rule.predicate.check.ValidationCheck

public object CollectionNotEmptyCheck : ValidationCheck<Collection<*>> {
    override fun isValid(value: Collection<*>): Boolean = value.isNotEmpty()
}
