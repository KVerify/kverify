package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment

public data class DefaultValidationContext(
    override val path: ValidationPath?,
) : ValidationContext {
    override fun derive(segment: ValidationPathSegment): ValidationContext =
        DefaultValidationContext(
            path =
                ValidationPath(
                    parent = path,
                    segment = segment,
                ),
        )
}
