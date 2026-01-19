package io.github.kverify.core.context.element

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.key.ValidationPathKey

public class ValidationPathElement(
    override val key: ValidationPathKey,
) : ValidationContext.Element {
    public companion object {
        public fun property(name: String): ValidationPathElement =
            ValidationPathElement(
                key = ValidationPathKey.Property(name),
            )

        public fun index(index: Int): ValidationPathElement =
            ValidationPathElement(
                key = ValidationPathKey.Index(index),
            )
    }
}
