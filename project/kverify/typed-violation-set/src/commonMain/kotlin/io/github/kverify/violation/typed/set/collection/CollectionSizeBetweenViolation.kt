package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionSizeBetweenViolation<C : Collection<*>>(
    override val value: C,
    val sizeRange: IntRange,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.sizeBetween(
            value = value,
            sizeRange = sizeRange,
            name = name,
        ),
) : TypedViolation<C>
