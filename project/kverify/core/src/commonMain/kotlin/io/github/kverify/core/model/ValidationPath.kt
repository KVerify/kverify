package io.github.kverify.core.model

public class ValidationPath(
    public val segments: List<ValidationPathSegment>,
) {
    override fun equals(other: Any?): Boolean =
        when {
            this === other -> true
            other !is ValidationPath -> false
            else -> segments == other.segments
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
