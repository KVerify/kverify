package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override fun <R> fold(
        initial: R,
        operation: (R, ValidationContext.Element) -> R,
    ): R = initial

    override fun plus(other: ValidationContext): ValidationContext = other
}
