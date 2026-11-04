package io.github.kverify.core.context

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

    override fun plus(other: ValidationContext): PersistentValidationContext {
        if (other is ValidationContext.Element) return plusElement(other)

        var result = this
        for (incoming in other) {
            result = result.plusElement(incoming)
        }

        return result
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? = nodeFor(key)?.element as E?

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext {
        val existingNode = nodeFor(key) ?: return this

        return rebuildRemoving(existingNode) ?: EmptyValidationContext
    }

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

    private fun nodeFor(key: ValidationContext.Key<*>): PersistentValidationContext? {
        var node: PersistentValidationContext? = this

        while (node != null) {
            val candidate = node.element.key
            if (candidate == key) return node

            node = node.parent
        }

        return null
    }

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

    private fun rebuildRemoving(existingNode: PersistentValidationContext): PersistentValidationContext? {
        if (existingNode === this) return parent

        val elementsToRebuild = elementsBeforeNode(existingNode)
        var currentNode = existingNode.parent

        for (incomingElement in elementsToRebuild) {
            currentNode = PersistentValidationContext(incomingElement, currentNode)
        }

        return currentNode
    }

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
