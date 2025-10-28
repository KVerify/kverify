package io.github.kverify.check.set.collection

import io.github.kverify.core.check.ValidationCheck

public object CollectionDistinctCheck : ValidationCheck<Collection<*>> {
    @Suppress("ReturnCount")
    override fun isValid(value: Collection<*>): Boolean {
        val size = value.size
        if (size <= 1) return true

        val set = HashSet<Any?>(size)
        for (element in value) {
            if (!set.add(element)) return false
        }

        return true
    }
}
