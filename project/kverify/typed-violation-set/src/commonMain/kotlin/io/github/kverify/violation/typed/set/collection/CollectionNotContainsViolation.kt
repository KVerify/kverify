package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionNotContainsViolation<E, C : Collection<E>>(
    override val value: C,
    val element: E,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.notContains(
            value = value,
            element = element,
            name = name,
        ),
) : TypedViolation<C>
