package io.github.kverify.core

public interface ValidationContext {
    public val elements: List<Element>

    public operator fun plus(element: Element): ValidationContext

    public operator fun plus(other: ValidationContext): ValidationContext

    public interface Element
}

public object EmptyValidationContext : ValidationContext {
    override val elements: List<ValidationContext.Element> = emptyList()

    override fun plus(element: ValidationContext.Element): ValidationContext = ListValidationContext(element)

    override fun plus(other: ValidationContext): ValidationContext = other
}

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

public sealed interface ValidationPathElement : ValidationContext.Element {
    public data class Property(
        val name: String,
    ) : ValidationPathElement

    public data class Index(
        val index: Int,
    ) : ValidationPathElement
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

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationContext.pathElements(): List<ValidationPathElement> = elements.filterIsInstance<ValidationPathElement>()
