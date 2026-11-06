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
     * If the key of an added element matches a stored key, the stored element is replaced by the
     * result of [incoming.key.onCollision][ValidationContext.Key.onCollision],
     * which receives the stored element as `oldElement` and the added element as `newElement`.
     * The replacement keeps the position of the stored element.
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
     * Returns the element with the given [key] from this context or `null`.
     */
    @Suppress("UNCHECKED_CAST")
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? =
        nodeFor(key)?.element?.get(key)

    /**
     * Returns a context containing elements from this context, but without an element with
     * the specified [key].
     * `this` context is never modified.
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
     *
     * Copies the elements of all nodes into an array, so it takes O(n) time and allocates an array
     * of n elements, where n is the number of elements in this context.
     * A context with a single element is not copied.
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
     * [ValidationContext.Key.onCollision] using [rebuildReplacing].
     * If that result has the same identity as the stored element, returns `this` unchanged.
     */
    private fun plusElement(incoming: ValidationContext.Element): PersistentValidationContext {
        val key = incoming.key

        val existingNode =
            nodeFor(key) ?: return PersistentValidationContext(
                element = incoming,
                parent = this,
            )

        val mergedElement =
            key.onCollision(
                existingNode.element,
                incoming,
            )

        if (mergedElement === existingNode.element) return this

        return rebuildReplacing(existingNode, mergedElement)
    }

    /**
     * Iterates through all nodes of this context, from newest to oldest, and returns the first
     * node whose element has the given [key], or `null` if there is none.
     *
     * Takes O(n) time, where n is the number of elements in this context.
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
     * `this` context is never modified.
     *
     * Nodes are immutable and each node points to its parent, so a node in the middle of the chain
     * cannot be changed in place. Every node added after [existingNode] depends on it, so all of
     * them are recreated, in their original order, on top of a new node holding [newElement].
     * Their elements are collected with [elementsBeforeNode]. Nodes older than [existingNode] are
     * shared with this context and not copied.
     *
     * Takes O(k) time and allocates k + 1 nodes, where k is the number of nodes added after
     * [existingNode].
     */
    private fun rebuildReplacing(
        existingNode: PersistentValidationContext,
        newElement: ValidationContext.Element,
    ): PersistentValidationContext {
        if (existingNode === this) return PersistentValidationContext(newElement, parent)

        val elementsToRebuild = elementsBeforeNode(existingNode)
        var currentNode = PersistentValidationContext(newElement, existingNode.parent)

        for (incomingElement in elementsToRebuild) {
            currentNode = PersistentValidationContext(incomingElement, currentNode)
        }

        return currentNode
    }

    /**
     * Returns a context without the element of [existingNode], or `null` if no elements remain.
     * `this` context is never modified.
     *
     * Works like [rebuildReplacing], except that the nodes added after [existingNode] are recreated
     * directly on top of the parent of [existingNode], so k nodes are allocated instead of k + 1.
     * See [rebuildReplacing] for why the cost depends on k.
     */
    private fun rebuildRemoving(existingNode: PersistentValidationContext): PersistentValidationContext? {
        if (existingNode === this) return parent

        val elementsToRebuild = elementsBeforeNode(existingNode)
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
     * Walks the chain from this node towards [existingNode], so it takes O(k) time and
     * allocates an array of k elements, where k is the number of nodes added after [existingNode].
     *
     * @throws IllegalStateException if [existingNode] is not part of this context.
     */
    private fun elementsBeforeNode(existingNode: PersistentValidationContext): Array<ValidationContext.Element> {
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
