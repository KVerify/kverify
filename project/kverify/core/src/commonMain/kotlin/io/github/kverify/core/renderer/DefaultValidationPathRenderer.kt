package io.github.kverify.core.renderer

import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.core.context.key.ValidationPathKey

public object DefaultValidationPathRenderer : ValidationPathRenderer {
    override fun render(path: List<ValidationPathElement>): String? {
        val keys = path.map { it.key }

        val firstKey = keys.firstOrNull() ?: return null
        val remainingKeys = keys.drop(1)

        return buildString {
            append(renderFirst(firstKey))
            remainingKeys.joinTo(this, "", transform = ::render)
        }
    }

    private fun renderFirst(key: ValidationPathKey): String =
        when (key) {
            is ValidationPathKey.Property -> key.name
            is ValidationPathKey.Index -> key.index.toString()
        }

    private fun render(key: ValidationPathKey): String =
        when (key) {
            is ValidationPathKey.Property -> ".${key.name}"
            is ValidationPathKey.Index -> "[${key.index}]"
        }
}
