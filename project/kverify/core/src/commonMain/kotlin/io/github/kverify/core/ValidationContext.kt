package io.github.kverify.core

/**
 * Defines how [Element]s are combined, accessed and removed.
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
 * A [ValidationContext] that holds no elements.
 */
public object EmptyValidationContext : ValidationContext {
    override fun plus(other: ValidationContext): ValidationContext = other

    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = null

    override fun minusKey(key: ValidationContext.Key<*>): EmptyValidationContext = this

    override fun iterator(): Iterator<ValidationContext.Element> = EmptyIterator
}

/**
 * A persistent [ValidationContext] that stores its elements as a linked list.
 *
 * Elements are iterated from oldest to newest.
 * [plus] and [minusKey] return new contexts and never modify this one.
 */
public class PersistentValidationContext private constructor(
    private val element: ValidationContext.Element,
    private val parent: PersistentValidationContext?,
) : ValidationContext {
    private val size: Int = (parent?.size ?: 0) + 1

    public constructor(
        element: ValidationContext.Element,
    ) : this(
        element = element,
        parent = null,
    )

    /**
     * Returns a context with [other] added to the elements of this context.
     *
     * A [ValidationContext.Element] is added as is. The elements of any other context are added
     * one by one, in its iteration order.
     *
     * If the key of an added element matches a stored key, the stored element is replaced.
     * The replacement is the result of calling [ValidationContext.Key.onCollision] on the stored
     * element's key, with the stored element as `oldElement` and the added element as `newElement`.
     * It takes the position of the stored element.
     */
    override fun plus(other: ValidationContext): PersistentValidationContext {
        if (other is ValidationContext.Element) return plusElement(other)

        var result = this
        for (incoming in other) {
            result = result.plusElement(incoming)
        }

        return result
    }

    /**
     * Returns the element with the given [key], or `null` if there is none.
     */
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = nodeFor(key)?.element?.get(key)

    /**
     * Returns a context containing elements from this context, but without an element with
     * the specified [key].
     *
     * Returns [EmptyValidationContext] if the matching element was the only one in this context.
     */
    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext {
        val existingNode = nodeFor(key) ?: return this

        return rebuildRemoving(existingNode) ?: EmptyValidationContext
    }

    /**
     * Returns an iterator over the elements of this context, from oldest to newest.
     */
    override fun iterator(): Iterator<ValidationContext.Element> {
        if (parent == null) return SingleElementIterator(element)

        val elements = arrayOfNulls<ValidationContext.Element>(size)

        var currentNode: PersistentValidationContext? = this
        var index = elements.lastIndex

        while (currentNode != null) {
            elements[index--] = currentNode.element
            currentNode = currentNode.parent
        }

        @Suppress("UNCHECKED_CAST")
        return (elements as Array<ValidationContext.Element>).iterator()
    }

    private fun plusElement(incoming: ValidationContext.Element): PersistentValidationContext {
        val existingNode =
            nodeFor(incoming.key) ?: return PersistentValidationContext(
                element = incoming,
                parent = this,
            )

        val existingElement = existingNode.element

        val mergedElement =
            existingElement.key.onCollision(
                existingElement,
                incoming,
            )

        if (mergedElement === existingElement) return this

        return rebuildReplacing(existingNode, mergedElement)
    }

    private fun nodeFor(key: ValidationContext.Key<*>): PersistentValidationContext? {
        var node: PersistentValidationContext? = this

        while (node != null) {
            node.element[key]?.let {
                return node
            }

            node = node.parent
        }

        return null
    }

    private fun rebuildReplacing(
        existingNode: PersistentValidationContext,
        newElement: ValidationContext.Element,
    ): PersistentValidationContext {
        if (existingNode === this) return PersistentValidationContext(newElement, parent)

        val elementsToRebuild = elementsAddedAfter(existingNode)
        var currentNode = PersistentValidationContext(newElement, existingNode.parent)

        for (incomingElement in elementsToRebuild) {
            currentNode = PersistentValidationContext(incomingElement, currentNode)
        }

        return currentNode
    }

    private fun rebuildRemoving(existingNode: PersistentValidationContext): PersistentValidationContext? {
        if (existingNode === this) return parent

        val elementsToRebuild = elementsAddedAfter(existingNode)
        var currentNode = existingNode.parent

        for (incomingElement in elementsToRebuild) {
            currentNode = PersistentValidationContext(incomingElement, currentNode)
        }

        return currentNode
    }

    private fun elementsAddedAfter(existingNode: PersistentValidationContext): Array<ValidationContext.Element> {
        val resultSize = size - existingNode.size

        if (resultSize == 0 && existingNode === this) return arrayOf()
        if (resultSize <= 0) error("Given node is not part of this context")

        val result = arrayOfNulls<ValidationContext.Element>(resultSize)
        var currentNode: PersistentValidationContext? = this
        var index = result.lastIndex

        while (currentNode != null && index >= 0) {
            result[index--] = currentNode.element
            currentNode = currentNode.parent
        }

        if (currentNode !== existingNode) error("Given node was not found")

        @Suppress("UNCHECKED_CAST")
        return result as Array<ValidationContext.Element>
    }
}

private object EmptyIterator : Iterator<Nothing> {
    override fun hasNext(): Boolean = false

    override fun next(): Nothing = throw NoSuchElementException()
}

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
