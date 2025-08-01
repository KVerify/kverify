package io.github.kverify.violation.typed.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider

public data class CollectionNotEmptyViolation<C : Collection<*>>(
    val value: C,
    val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.notEmpty(
            value = value,
            name = name,
        ),
) : Violation
