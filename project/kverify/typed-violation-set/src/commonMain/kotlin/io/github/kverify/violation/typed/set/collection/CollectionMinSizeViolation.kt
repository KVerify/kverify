package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionMinSizeViolation<C : Collection<*>>(
    override val value: C,
    val minSize: Int,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.minSize(
            value = value,
            minSize = minSize,
            name = name,
        ),
) : TypedViolation<C>
