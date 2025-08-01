package io.github.kverify.violation.typed.set.collection

import io.github.kverify.violation.set.localization.CollectionViolationLocalizationProvider
import io.github.kverify.violation.typed.set.TypedViolation
import kotlin.jvm.JvmName

public data class CollectionContainsAllViolation<E, C : Collection<E>>(
    override val value: C,
    val elements: Collection<E>,
    override val name: String? = null,
    override val reason: String =
        CollectionViolationLocalizationProvider.Default.containsAll(
            value = value,
            elements = elements,
            name = name,
        ),
) : TypedViolation<C>

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllViolation(
    value: C,
    vararg elements: E,
    name: String? = null,
): CollectionContainsAllViolation<E, C> =
    CollectionContainsAllViolation(
        value = value,
        elements = elements.asList(),
        name = name,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsAllViolation(
    value: C,
    vararg elements: E,
    name: String? = null,
    reason: String,
): CollectionContainsAllViolation<E, C> =
    CollectionContainsAllViolation(
        value = value,
        elements = elements.asList(),
        name = name,
        reason = reason,
    )
