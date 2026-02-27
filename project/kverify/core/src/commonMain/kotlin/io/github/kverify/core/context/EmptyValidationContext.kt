package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override val elements: List<ValidationContext.Element> = emptyList()

    override fun plus(other: ValidationContext): ValidationContext = other
}
