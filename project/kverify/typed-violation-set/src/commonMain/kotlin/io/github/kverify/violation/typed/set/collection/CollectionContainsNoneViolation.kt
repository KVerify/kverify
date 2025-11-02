package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionContainsNoneViolation<E, C : Collection<E>>(
    override val value: C,
    val elements: Collection<E>,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.containsNone(
            value = value,
            elements = elements,
            name = name,
        ),
) : TypedViolation<C>
