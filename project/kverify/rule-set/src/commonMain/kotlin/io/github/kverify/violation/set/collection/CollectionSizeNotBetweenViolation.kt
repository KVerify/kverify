package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionSizeNotBetweenViolation<C : Collection<*>>(
    val value: C,
    val minSize: Int,
    val maxSize: Int,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName size must NOT be between $minSize and $maxSize (inclusive), but it is ${value.size}."
        },
) : Violation
