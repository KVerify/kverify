package io.github.kverify.core.context

public sealed interface ValidationPathElement : ValidationContext.Element

public data class PropertyPathElement(
    val name: String,
) : ValidationPathElement

public data class IndexPathElement(
    val index: Int,
) : ValidationPathElement
