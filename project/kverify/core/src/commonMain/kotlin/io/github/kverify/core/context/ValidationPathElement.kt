package io.github.kverify.core.context

/**
 * A [ValidationContext.Element] that contributes one segment to the validation path.
 *
 * The two concrete subtypes are [NamePathElement] and [IndexPathElement], representing
 * named (field/property) and indexed (collection position) path segments respectively.
 */
public sealed interface ValidationPathElement : ValidationContext.Element

/**
 * A named segment in a validation path, typically representing a field or property.
 *
 * Example: validating a field `user.address.street` would produce a path of
 * `[NamePathElement("user"), NamePathElement("address"), NamePathElement("street")]`.
 *
 * @param name The name of this path segment, as it should appear in violation reports.
 */
public class NamePathElement(
    public val name: String,
) : ValidationPathElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NamePathElement) return false

        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = "NamePathElement(name=$name)"
}

/**
 * An indexed segment in a validation path, typically representing a position within a collection.
 *
 * Example: validating the third element of a list would produce an [IndexPathElement] with `index = 2`.
 *
 * @param index The zero-based index of this path segment.
 */
public class IndexPathElement(
    public val index: Int,
) : ValidationPathElement {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndexPathElement) return false

        return index == other.index
    }

    override fun hashCode(): Int = index.hashCode()

    override fun toString(): String = "IndexPathElement(index=$index)"
}
