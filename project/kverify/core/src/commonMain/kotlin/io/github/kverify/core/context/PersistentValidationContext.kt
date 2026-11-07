package io.github.kverify.core.context

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
     *
     * Returns `this` if no element was added or replaced.
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
    @Suppress("UNCHECKED_CAST")
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? =
        nodeFor(key)?.element?.get(key)

    /**
     * Returns a context containing elements from this context, but without an element with
     * the specified [key].
     *
     * Returns `this` if no element with a matching key was found.
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

    /**
     * Returns a context with [incoming] added as the newest element if no stored element has the
     * same key as [incoming].
     *
     * If a stored element has the same key as [incoming], replaces it with the result of
     * [ValidationContext.Key.onCollision] of the stored element's key, using [rebuildReplacing].
     */
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

    /**
     * Iterates through all nodes of this context, from newest to oldest, and returns the first
     * node whose element has the given [key], or `null` if there is none.
     */
    private fun nodeFor(key: ValidationContext.Key<*>): PersistentValidationContext? {
        var node: PersistentValidationContext? = this

        while (node != null) {
            val candidate = node.element.key
            if (candidate == key) return node

            node = node.parent
        }

        return null
    }

    /**
     * Returns a context in which the element of [existingNode] is replaced by [newElement].
     * The replacement keeps the position of the element it replaces.
     *
     * @throws IllegalStateException if [existingNode] is not part of this context.
     */
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

    /**
     * Returns a context without the element of [existingNode], or `null` if no elements remain.
     *
     * @throws IllegalStateException if [existingNode] is not part of this context.
     */
    private fun rebuildRemoving(existingNode: PersistentValidationContext): PersistentValidationContext? {
        if (existingNode === this) return parent

        val elementsToRebuild = elementsAddedAfter(existingNode)
        var currentNode = existingNode.parent

        for (incomingElement in elementsToRebuild) {
            currentNode = PersistentValidationContext(incomingElement, currentNode)
        }

        return currentNode
    }

    /**
     * Returns the elements of the nodes added after [existingNode], from oldest to newest.
     * The element of [existingNode] itself is not included. The result is empty if
     * [existingNode] is this node.
     *
     * @throws IllegalStateException if [existingNode] is not part of this context.
     */
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