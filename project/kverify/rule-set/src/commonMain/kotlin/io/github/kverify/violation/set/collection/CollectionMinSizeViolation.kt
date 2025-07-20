package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionMinSizeViolation<C : Collection<*>>(
    val value: C,
    val minSize: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName must have at least $minSize elements, but it has ${value.size}."
        },
) : Violation
