package io.github.kverify.core.model

public class ValidationPath(
    public val segments: List<ValidationPathSegment>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ValidationPath) return false
        return segments == other.segments
    }

    override fun hashCode(): Int = segments.hashCode()

    override fun toString(): String =
        segments.joinToString(
            separator = ", ",
            prefix = "ValidationPath(",
            postfix = ")",
        ) { segment ->
            when (segment) {
                is NamePathSegment -> "\"${segment.name}\""
                is IndexPathSegment -> segment.index.toString()
            }
        }

    internal companion object {
        val Empty: ValidationPath = ValidationPath(listOf())
    }
}

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
