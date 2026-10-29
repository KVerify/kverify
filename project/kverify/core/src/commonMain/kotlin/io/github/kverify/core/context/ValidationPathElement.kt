package io.github.kverify.core.context

import io.github.kverify.core.model.IndexPathSegment
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment

public class ValidationPathElement(
    public val segment: ValidationPathSegment,
    public val parent: ValidationPathElement?,
) : ValidationContext.Element {
    public val size: Int = (parent?.size ?: 0) + 1

    override val key: ValidationContext.Key<*>
        get() = Key

    override fun plus(other: ValidationContext): ValidationContext =
        if (other is ValidationPathElement) {
            this.addingAllSegments(other)
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

    public companion object Key : ValidationContext.Key<ValidationPathElement> {
        override fun onCollision(
            oldElement: ValidationContext.Element,
            newElement: ValidationContext.Element,
        ): ValidationContext.Element =
            when {
                oldElement !is ValidationPathElement -> newElement
                newElement !is ValidationPathElement -> oldElement
                else -> oldElement.addingAllSegments(newElement)
            }
    }
}

public fun pathName(name: String): ValidationPathElement = ValidationPathElement(NamePathSegment(name), null)

public fun ValidationContext.pathName(name: String): ValidationContext =
    this +
        ValidationPathElement(
            NamePathSegment(name),
            parent = null,
        )

public fun pathIndex(index: Int): ValidationPathElement = ValidationPathElement(IndexPathSegment(index), null)

public fun ValidationContext.pathIndex(index: Int): ValidationContext =
    this +
        ValidationPathElement(
            segment = IndexPathSegment(index),
            parent = null,
        )

public fun ValidationPathElement.toValidationPath(): ValidationPath = ValidationPath(this.toSegmentArray().asList())

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

private fun ValidationPathElement.addingAllSegments(segmentsSource: ValidationPathElement): ValidationPathElement {
    if (segmentsSource.parent == null) {
        return ValidationPathElement(segmentsSource.segment, this)
    }

    var result = this

    for (incomingSegment in segmentsSource.toSegmentArray()) {
        result = ValidationPathElement(incomingSegment, result)
    }

    return result
}
