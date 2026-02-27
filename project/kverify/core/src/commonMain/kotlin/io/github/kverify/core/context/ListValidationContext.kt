package io.github.kverify.core.context

public class ListValidationContext(
    override val elements: List<ValidationContext.Element>,
) : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext =
        ListValidationContext(
            elements + other.elements,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun ListValidationContext(vararg elements: ValidationContext.Element): ListValidationContext =
    ListValidationContext(
        elements.asList(),
    )
