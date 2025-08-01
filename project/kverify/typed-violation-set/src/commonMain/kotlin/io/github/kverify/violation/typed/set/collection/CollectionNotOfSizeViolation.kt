package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionNotOfSizeViolation<C : Collection<*>>(
    override val value: C,
    val size: Int,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.notOfSize(
            value = value,
            size = size,
            name = name,
        ),
) : TypedViolation<C>
