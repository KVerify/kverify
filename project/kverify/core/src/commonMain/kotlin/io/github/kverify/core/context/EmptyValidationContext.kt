package io.github.kverify.core.context

/**
 * A [ValidationContext] that holds no elements.
 */
public object EmptyValidationContext : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext = other

    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = null

    override fun minusKey(key: ValidationContext.Key<*>): EmptyValidationContext = this

    override fun iterator(): Iterator<ValidationContext.Element> = EmptyIterator
}

private object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw NoSuchElementException()
}
