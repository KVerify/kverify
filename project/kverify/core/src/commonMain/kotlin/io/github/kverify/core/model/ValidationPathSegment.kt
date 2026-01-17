package io.github.kverify.core.model

public sealed interface ValidationPathSegment

public data class PropertyPathSegment(
    val name: String,
) : ValidationPathSegment

public data class IndexPathSegment(
    val index: Int,
) : ValidationPathSegment

public data class KeyPathSegment(
    val key: String,
) : ValidationPathSegment

public interface ExtendedValidationPathSegment : ValidationPathSegment {
    public fun render(): String
}
