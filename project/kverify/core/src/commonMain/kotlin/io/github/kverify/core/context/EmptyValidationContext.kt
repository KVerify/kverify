package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override val elements: List<ValidationContext.Element> = emptyList()

    override fun plus(element: ValidationContext.Element): ValidationContext = ListValidationContext(element)

    override fun plus(other: ValidationContext): ValidationContext = other
}
