package io.github.kverify.core.context

public sealed interface ValidationPathElement : ValidationContext.Element

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
