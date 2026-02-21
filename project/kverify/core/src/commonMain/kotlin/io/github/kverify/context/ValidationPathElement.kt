package io.github.kverify.context

public sealed interface ValidationPathElement : ValidationContext.Element {
    public data class Property(
        val name: String,
    ) : ValidationPathElement

    public data class Index(
        val index: Int,
    ) : ValidationPathElement
}
