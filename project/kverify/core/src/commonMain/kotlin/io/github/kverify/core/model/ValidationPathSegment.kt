package io.github.kverify.core.model

public sealed interface ValidationPathSegment

public class NamePathSegment(
    public val name: String,
) : ValidationPathSegment {
    override fun toString(): String = "NamePathSegment(name=$name)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is NamePathSegment -> false
            else -> name == other.name
        }

    override fun hashCode(): Int = name.hashCode()
}

public class IndexPathSegment(
    public val index: Int,
) : ValidationPathSegment {
    override fun toString(): String = "IndexPathSegment(index=$index)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is IndexPathSegment -> false
            else -> index == other.index
        }

    override fun hashCode(): Int = index.hashCode()
}
