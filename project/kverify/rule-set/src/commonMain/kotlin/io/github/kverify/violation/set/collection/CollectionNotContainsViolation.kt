package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionNotContainsViolation<E, C : Collection<E>>(
    val value: C,
    val element: E,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)

            "$displayName must not contain the element: '$element', but it does."
        },
) : Violation
