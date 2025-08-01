package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionDistinctViolation<C : Collection<*>>(
    override val value: C,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.distinct(
            value = value,
            name = name,
        ),
) : TypedViolation<C>
