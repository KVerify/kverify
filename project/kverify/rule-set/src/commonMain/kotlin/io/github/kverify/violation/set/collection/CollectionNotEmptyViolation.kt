package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionNotEmptyViolation<C : Collection<*>>(
    val value: C,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)
            "$displayName must not be empty, but it is."
        },
) : Violation
