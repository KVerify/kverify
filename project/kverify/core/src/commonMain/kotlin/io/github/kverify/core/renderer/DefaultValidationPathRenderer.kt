package io.github.kverify.core.renderer

import io.github.kverify.core.model.ExtendedValidationPathSegment
import io.github.kverify.core.model.IndexPathSegment
import io.github.kverify.core.model.KeyPathSegment
import io.github.kverify.core.model.PropertyPathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.model.ValidationPathSegment

public object DefaultValidationPathRenderer : ValidationPathRenderer {
    override fun render(path: ValidationPath): String {
        val segments = path.fullSegments
        val firstSegment = segments.first()
        val remainingSegments = segments.drop(1).joinToString("") { render(it) }

        return renderFirst(firstSegment) + remainingSegments
    }

    private fun renderFirst(segment: ValidationPathSegment): String =
        when (segment) {
            is PropertyPathSegment -> segment.name
            is IndexPathSegment -> segment.index.toString()
            is KeyPathSegment -> segment.key
            is ExtendedValidationPathSegment -> segment.render()
        }

    private fun render(segment: ValidationPathSegment): String =
        when (segment) {
            is PropertyPathSegment -> ".${segment.name}"
            is IndexPathSegment -> "[${segment.index}]"
            is KeyPathSegment -> "[\"${segment.key}\"]"
            is ExtendedValidationPathSegment -> segment.render()
        }
}
