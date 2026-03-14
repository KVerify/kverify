package io.github.kverify.core.context

/**
 * A [ValidationContext] with no elements.
 */
public object EmptyValidationContext : ValidationContext {
    /**
     * Returns the other context, since there are no elements to add.
     */
    override fun plus(other: ValidationContext): ValidationContext = other

    /**
     * Returns an [EmptyIterator].
     */
    override fun iterator(): Iterator<ValidationContext.Element> = emptyList<ValidationContext.Element>().iterator()
}
