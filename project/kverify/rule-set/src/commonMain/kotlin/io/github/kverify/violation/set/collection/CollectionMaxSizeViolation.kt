package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionMaxSizeViolation<C : Collection<*>>(
    val value: C,
    val maxSize: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName must have $maxSize elements at most, but it has ${value.size}."
        },
) : Violation
