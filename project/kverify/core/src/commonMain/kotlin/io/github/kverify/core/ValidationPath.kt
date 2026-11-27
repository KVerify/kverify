package io.github.kverify.core

/**
 * A single segment of a validation path: either a [NamePathSegment] or an [IndexPathSegment].
 */
public sealed interface ValidationPathSegment

/**
 * A [ValidationPathSegment] with a [name].
 */
public class NamePathSegment(
    public val name: String,
) : ValidationPathSegment {
    override fun toString(): String = "NamePathSegment($name)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is NamePathSegment -> false
            else -> name == other.name
        }

    override fun hashCode(): Int = name.hashCode()
}

/**
 * A [ValidationPathSegment] with an [index].
 */
public class IndexPathSegment(
    public val index: Int,
) : ValidationPathSegment {
    override fun toString(): String = "IndexPathSegment($index)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is IndexPathSegment -> false
            else -> index == other.index
        }

    override fun hashCode(): Int = index.hashCode()
}

/**
 * A [ValidationContext.Element] that stores a validation path as a linked list.
 */
public class ValidationPathElement(
    internal val segment: ValidationPathSegment,
    internal val parent: ValidationPathElement?,
) : ValidationContext.Element {
    internal val size: Int = (parent?.size ?: 0) + 1

    override val key: ValidationContext.Key<*>
        get() = Key

    /**
     * If [other] is a [ValidationPathElement], returns a new [ValidationPathElement] with
     * the segments of [other] added to the end of this path.
     *
     * Otherwise, returns a [PersistentValidationContext] with this element followed by
     * the elements of [other].
     */
    override fun plus(other: ValidationContext): ValidationContext =
        if (other is ValidationPathElement) {
            this.rebuildAddingSegments(other)
        } else {
            super.plus(other)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValidationPathElement) return false
        if (size != other.size) return false

        var left: ValidationPathElement? = this
        var right: ValidationPathElement? = other

        while (left != null && right != null) {
            if (left === right) return true
            if (left.segment != right.segment) return false

            left = left.parent
            right = right.parent
        }

        return true
    }

    override fun hashCode(): Int {
        var result = 1
        var node: ValidationPathElement? = this

        while (node != null) {
            result = 31 * result + node.segment.hashCode()
            node = node.parent
        }

        return result
    }

    private fun rebuildAddingSegments(segmentsSource: ValidationPathElement): ValidationPathElement {
        if (segmentsSource.parent == null) {
            return ValidationPathElement(segmentsSource.segment, this)
        }

        var result = this

        for (incomingSegment in segmentsSource.toSegmentArray()) {
            result = ValidationPathElement(incomingSegment, result)
        }

        return result
    }

    public companion object Key : ValidationContext.Key<ValidationPathElement> {
        /**
         * If [oldElement] is not a [ValidationPathElement], returns [newElement].
         * If [newElement] is not a [ValidationPathElement], returns [oldElement].
         *
         * Otherwise, returns a new [ValidationPathElement] with the segments of [newElement]
         * added to the end of the path of [oldElement].
         */
        override fun onCollision(
            oldElement: ValidationContext.Element,
            newElement: ValidationContext.Element,
        ): ValidationContext.Element =
            when {
                oldElement !is ValidationPathElement -> newElement
                newElement !is ValidationPathElement -> oldElement
                else -> oldElement.rebuildAddingSegments(newElement)
            }
    }
}

/**
 * Returns a new [ValidationPathElement] with a single [NamePathSegment] created from [name].
 */
public fun pathName(name: String): ValidationPathElement = ValidationPathElement(NamePathSegment(name), null)

/**
 * Returns a new [ValidationPathElement] with a single [IndexPathSegment] created from [index].
 */
public fun pathIndex(index: Int): ValidationPathElement = ValidationPathElement(IndexPathSegment(index), null)

/**
 * Returns the segments of the [ValidationPathElement] stored in this context, or an empty list
 * if the context has no [ValidationPathElement].
 */
public fun ValidationContext.validationPath(): List<ValidationPathSegment> =
    this[ValidationPathElement]?.toValidationPath()
        ?: emptyList()

/**
 * Returns a list of the segments of this path, in the order they were added.
 */
public fun ValidationPathElement.toValidationPath(): List<ValidationPathSegment> = this.toSegmentArray().asList()

private fun ValidationPathElement.toSegmentArray(): Array<ValidationPathSegment> {
    val segments = Array(size) { segment }

    var node = parent
    var index = size - 1

    while (node != null) {
        segments[--index] = node.segment
        node = node.parent
    }

    return segments
}
