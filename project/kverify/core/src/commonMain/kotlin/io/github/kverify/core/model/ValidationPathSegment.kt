package io.github.kverify.core.model

public sealed interface ValidationPathSegment

public class NamePathSegment(
    public val name: String,
) : ValidationPathSegment {
    override fun toString(): String = "NamePathSegment(name=$name)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NamePathSegment) return false
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
}

public class IndexPathSegment(
    public val index: Int,
) : ValidationPathSegment {
    override fun toString(): String = "IndexPathSegment(name=$index)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IndexPathSegment) return false
        return index == other.index
    }

    override fun hashCode(): Int = index.hashCode()
}
