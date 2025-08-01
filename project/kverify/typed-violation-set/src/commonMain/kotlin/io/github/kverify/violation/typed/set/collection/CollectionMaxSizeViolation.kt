package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionMaxSizeViolation<C : Collection<*>>(
    override val value: C,
    val maxSize: Int,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.maxSize(
            value = value,
            maxSize = maxSize,
            name = name,
        ),
) : TypedViolation<C>
