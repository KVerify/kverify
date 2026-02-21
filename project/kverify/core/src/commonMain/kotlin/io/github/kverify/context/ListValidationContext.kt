package io.github.kverify.context

import kotlin.collections.plus

public data class ListValidationContext(
    override val elements: List<ValidationContext.Element>,
) : ValidationContext {
    override fun plus(element: ValidationContext.Element): ValidationContext =
        ListValidationContext(
            elements + element,
        )

    override fun plus(other: ValidationContext): ValidationContext =
        ListValidationContext(
            elements + other.elements,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun ListValidationContext(element: ValidationContext.Element): ListValidationContext =
    ListValidationContext(
        listOf(element),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ListValidationContext(vararg elements: ValidationContext.Element): ListValidationContext =
    ListValidationContext(
        elements.asList(),
    )
