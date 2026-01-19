package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = null

    override fun <R> fold(
        initial: R,
        operation: (R, ValidationContext.Element) -> R,
    ): R = initial

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext = this
}
