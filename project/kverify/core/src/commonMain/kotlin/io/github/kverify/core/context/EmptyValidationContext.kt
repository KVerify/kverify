package io.github.kverify.core.context

public object EmptyValidationContext : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext = other

    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = null

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext = this

    override fun iterator(): Iterator<ValidationContext.Element> = EmptyIterator
}

/**
 * A stateless, allocation-free [Iterator] that always reports no elements.
 *
 * Used as the backing iterator for [EmptyValidationContext].
 */
private object EmptyIterator : Iterator<Nothing> {
    /**
     * Always returns `false` — this iterator contains no elements.
     */
    override fun hasNext(): Boolean = false

    /**
     * Always throws [NoSuchElementException] — this iterator contains no elements.
     */
    override fun next(): Nothing = throw NoSuchElementException()
}
