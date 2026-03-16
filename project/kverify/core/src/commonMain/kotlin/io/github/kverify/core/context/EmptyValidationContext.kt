package io.github.kverify.core.context

/**
 * A [ValidationContext] with no elements.
 */
public object EmptyValidationContext : ValidationContext {
    /**
     * Returns [other] unchanged, since this context contributes no elements.
     */
    override fun plus(other: ValidationContext): ValidationContext = other

    /**
     * Returns [EmptyIterator].
     */
    override fun iterator(): Iterator<Nothing> = EmptyIterator

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
}
