package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment

public interface ValidationContext {
    public val path: ValidationPath?

    public fun derive(segment: ValidationPathSegment): ValidationContext
}

public inline fun ValidationContext(
    path: ValidationPath? = null,
    crossinline derive: (ValidationPathSegment) -> ValidationContext,
): ValidationContext =
    object : ValidationContext {
        override val path: ValidationPath? = path

        override fun derive(segment: ValidationPathSegment): ValidationContext = derive(segment)
    }
