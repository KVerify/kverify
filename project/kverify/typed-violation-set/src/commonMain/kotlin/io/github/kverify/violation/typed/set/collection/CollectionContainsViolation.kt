package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionContainsViolation<E, C : Collection<E>>(
    override val value: C,
    val element: E,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.contains(
            value = value,
            element = element,
            name = name,
        ),
) : TypedViolation<C>
