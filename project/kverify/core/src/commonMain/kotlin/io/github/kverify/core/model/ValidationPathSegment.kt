package io.github.kverify.core.model

/**
 * A single segment of a [ValidationPath]: either a [NamePathSegment] or an [IndexPathSegment].
 */
public sealed interface ValidationPathSegment

/**
 * A [ValidationPathSegment] with a [name].
 */
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

/**
 * A [ValidationPathSegment] with an [index].
 */
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
