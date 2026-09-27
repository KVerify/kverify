package io.github.kverify.core.model

public sealed interface ValidationPathElement

public class NamePathElement(
    public val name: String,
) : ValidationPathElement

public class IndexPathElement(
    public val index: Int,
) : ValidationPathElement
