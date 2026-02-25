package io.github.kverify.core.context

public interface ValidationContext {
    public val elements: List<Element>

    public operator fun plus(element: Element): ValidationContext

    public operator fun plus(other: ValidationContext): ValidationContext

    public interface Element
}

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationContext.pathElements(): List<ValidationPathElement> = elements.filterIsInstance<ValidationPathElement>()
