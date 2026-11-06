package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationPath

/**
 * A collection of [Element]s, where each element is identified by a unique [Key].
 */
public interface ValidationContext {
    public operator fun plus(other: ValidationContext): ValidationContext

    public operator fun <E : Element> get(key: Key<E>): E?

    public fun minusKey(key: Key<*>): ValidationContext

    public operator fun iterator(): Iterator<Element>

    /**
     * Identifies an [Element] of type [E] in a [ValidationContext].
     */
    public interface Key<out E : Element> {
        public fun onCollision(
            oldElement: Element,
            newElement: Element,
        ): Element
    }

    /**
     * A single piece of data stored in a [ValidationContext].
     *
     * An element is also a context itself: one that contains only this element.
     */
    public interface Element : ValidationContext {
        /**
         * The key that identifies this element.
         */
        public val key: Key<*>

        override fun plus(other: ValidationContext): ValidationContext = PersistentValidationContext(this).plus(other)

        override operator fun <E : Element> get(key: Key<E>): E? =
            if (this.key == key) {
                @Suppress("UNCHECKED_CAST")
                this as E
            } else {
                null
            }

        override fun minusKey(key: Key<*>): ValidationContext =
            if (this.key == key) {
                EmptyValidationContext
            } else {
                this
            }

        override fun iterator(): Iterator<Element> = SingleElementIterator(this)
    }
}

/**
 * Returns the [ValidationPath] stored in this context, or [ValidationPath.Empty]
 * if the context has no [ValidationPathElement].
 */
public fun ValidationContext.validationPath(): ValidationPath =
    this[ValidationPathElement]?.toValidationPath()
        ?: ValidationPath.Empty

internal class SingleElementIterator(
    private val element: ValidationContext.Element,
) : Iterator<ValidationContext.Element> {
    private var hasNext: Boolean = true

    override fun next(): ValidationContext.Element {
        if (!hasNext) throw NoSuchElementException()
        hasNext = false
        return element
    }

    override fun hasNext(): Boolean = hasNext
}
