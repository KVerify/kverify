package io.github.kverify.core.model

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationPathElement

/**
 * An ordered sequence of path segments that identifies the location of a value within
 * a validated structure.
 *
 * A path is built from [ValidationPathElement]s accumulated in a [io.github.kverify.core.context.ValidationContext] —
 * each [NamePathElement] represents a named field or property, and each [IndexPathElement]
 * represents a position within a collection.
 *
 * An empty [elements] list means the violation was produced at the root scope,
 * with no path context applied.
 *
 * @param elements The ordered list of path segments. Empty for root-level violations.
 */
public class ValidationPath(
    public val elements: List<ValidationPathElement>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValidationPath) return false
        return elements == other.elements
    }

    override fun hashCode(): Int = elements.hashCode()

    override fun toString(): String =
        elements.joinToString(
            separator = ", ",
            prefix = "ValidationPath(",
            postfix = ")",
        ) { element ->
            when (element) {
                is NamePathElement -> "\"${element.name}\""
                is IndexPathElement -> element.index.toString()
            }
        }
}
