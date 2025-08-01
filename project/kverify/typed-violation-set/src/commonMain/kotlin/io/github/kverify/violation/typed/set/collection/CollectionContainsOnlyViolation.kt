package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation

public data class CollectionContainsOnlyViolation<E, C : Collection<E>>(
    override val value: C,
    val elements: Collection<E>,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.containsOnly(
            value = value,
            elements = elements,
            name = name,
        ),
) : TypedViolation<C>

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyViolation(
    value: C,
    vararg elements: E,
    name: String? = null,
): CollectionContainsOnlyViolation<E, C> =
    CollectionContainsOnlyViolation(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsOnlyViolation(
    value: C,
    vararg elements: E,
    name: String? = null,
    reason: String,
): CollectionContainsOnlyViolation<E, C> =
    CollectionContainsOnlyViolation(
        value = value,
        elements = elements.asList(),
        name = name,
        reason = reason,
    )
