package io.github.kverify.core.renderer

import io.github.kverify.core.context.element.ValidationPathElement

public fun interface ValidationPathRenderer {
    public fun render(path: List<ValidationPathElement>): String?
}
