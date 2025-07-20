package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionNotOfSizeViolation<C : Collection<*>>(
    val value: C,
    val size: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName must not have exactly $size elements, but it does."
        },
) : Violation
