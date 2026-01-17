package io.github.kverify.core.renderer

import io.github.kverify.core.model.ValidationPath

public fun interface ValidationPathRenderer {
    public fun render(path: ValidationPath): String
}
