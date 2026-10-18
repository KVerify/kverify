package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationPath

public interface ValidationContext {
    public operator fun plus(other: ValidationContext): ValidationContext

    public operator fun <E : Element> get(key: Key<E>): E?

    public fun minusKey(key: Key<*>): ValidationContext

    public operator fun iterator(): Iterator<Element>

    public interface Key<out E : Element> {
        public fun onCollision(
            oldElement: Element,
            newElement: Element,
        ): Element
    }

    public interface Element : ValidationContext {
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
