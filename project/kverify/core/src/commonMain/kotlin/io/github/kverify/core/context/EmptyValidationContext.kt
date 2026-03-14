package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext = other

    override fun iterator(): Iterator<ValidationContext.Element> = emptyList<ValidationContext.Element>().iterator()
}
