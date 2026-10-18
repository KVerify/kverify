package io.github.kverify.core.context

public class PersistentValidationContext private constructor(
    private val element: ValidationContext.Element,
    private val parent: PersistentValidationContext?,
    bit: Int,
) : ValidationContext {
    private constructor(
        element: ValidationContext.Element,
        parent: PersistentValidationContext?,
    ) : this(
        element = element,
        parent = parent,
        bit = bitOf(element.key),
    )

    public constructor(
        element: ValidationContext.Element,
    ) : this(
        element = element,
        parent = null,
        bit = bitOf(element.key),
    )

    private val elementKey = element.key
    private val size: Int = (parent?.size ?: 0) + 1
    private val mask: Int = (parent?.mask ?: 0) or bit

    override fun plus(other: ValidationContext): PersistentValidationContext {
        if (other is ValidationContext.Element) return plusElement(other)

        var result = this
        for (incoming in other) {
            result = result.plusElement(incoming)
        }

        return result
    }

    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? {
        if ((mask and bitOf(key)) == 0) return null

        @Suppress("UNCHECKED_CAST")
        return nodeFor(key)?.element as E?
    }

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext {
        if ((mask and bitOf(key)) == 0) return this
        val target = nodeFor(key) ?: return this

        val base = target.parent

        return if (target === this) {
            base ?: EmptyValidationContext
        } else {
            copyNewerOnto(base, target)
        }
    }

    override fun iterator(): Iterator<ValidationContext.Element> {
        if (parent == null) return SingleElementIterator(element)

        val elements = arrayOfNulls<ValidationContext.Element>(size)

        var node: PersistentValidationContext? = this
        var index = size

        while (node != null) {
            elements[--index] = node.element
            node = node.parent
        }

        @Suppress("UNCHECKED_CAST")
        return (elements as Array<ValidationContext.Element>).iterator()
    }

    private fun plusElement(incoming: ValidationContext.Element): PersistentValidationContext {
        val key = incoming.key
        val bit = bitOf(key)

        if ((mask and bit) != 0) {
            val target = nodeFor(key)

            if (target != null) {
                val merged = key.onCollision(target.element, incoming)

                return if (merged === target.element) {
                    this
                } else {
                    replaced(target, merged)
                }
            }
        }

        return PersistentValidationContext(incoming, this, bit)
    }

    private fun nodeFor(key: ValidationContext.Key<*>): PersistentValidationContext? {
        var node: PersistentValidationContext? = this

        while (node != null) {
            val candidate = node.elementKey
            if (candidate === key || candidate == key) return node

            node = node.parent
        }

        return null
    }

    private fun replaced(
        target: PersistentValidationContext,
        merged: ValidationContext.Element,
    ): PersistentValidationContext {
        val replacement = PersistentValidationContext(merged, target.parent)

        return if (target === this) {
            replacement
        } else {
            copyNewerOnto(replacement, target)
        }
    }

    private fun copyNewerOnto(
        base: PersistentValidationContext?,
        target: PersistentValidationContext,
    ): PersistentValidationContext {
        val newer = ArrayList<PersistentValidationContext>(size - target.size)
        var node: PersistentValidationContext? = this

        while (node != null && node !== target) {
            newer.add(node)
            node = node.parent
        }

        val oldest = newer.last()
        var head = PersistentValidationContext(oldest.element, base)

        for (i in newer.size - 2 downTo 0) {
            val above = newer[i]
            head = PersistentValidationContext(above.element, head)
        }

        return head
    }
}

private const val HASH_MULTIPLIER = -1640531527

private fun bitOf(key: ValidationContext.Key<*>): Int = 1 shl ((key.hashCode() * HASH_MULTIPLIER) ushr 27)
