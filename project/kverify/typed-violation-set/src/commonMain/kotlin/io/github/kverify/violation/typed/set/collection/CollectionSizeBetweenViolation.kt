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

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeBetweenViolation(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
    reason: String,
): CollectionSizeBetweenViolation<C> =
    CollectionSizeBetweenViolation(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
        reason = reason,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <C : Collection<*>> CollectionSizeBetweenViolation(
    value: C,
    minSize: Int,
    maxSize: Int,
    name: String? = null,
): CollectionSizeBetweenViolation<C> =
    CollectionSizeBetweenViolation(
        value = value,
        sizeRange = minSize..maxSize,
        name = name,
    )
