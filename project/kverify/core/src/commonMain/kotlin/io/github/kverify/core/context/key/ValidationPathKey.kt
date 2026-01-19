package io.github.kverify.core.context.key

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.element.ValidationPathElement

public sealed interface ValidationPathKey : ValidationContext.Key<ValidationPathElement> {
    public data class Property(
        public val name: String,
    ) : ValidationPathKey

    public data class Index(
        public val index: Int,
    ) : ValidationPathKey
}
