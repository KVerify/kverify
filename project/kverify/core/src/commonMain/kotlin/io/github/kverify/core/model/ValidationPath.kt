package io.github.kverify.core.model

public data class ValidationPath(
    public val parent: ValidationPath?,
    public val segment: ValidationPathSegment,
) {
    val fullSegments: List<ValidationPathSegment>
        get() =
            if (parent == null) {
                listOf(segment)
            } else {
                parent.fullSegments + segment
            }
}
