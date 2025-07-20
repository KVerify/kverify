package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionOfSizeViolation<C : Collection<*>>(
    val value: C,
    val size: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName must have exactly $size elements, but it has ${value.size}."
        },
) : Violation
