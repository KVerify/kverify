package io.github.kverify.rules

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.ValidationPathElement

internal fun ValidationContext.validationPath(): List<ValidationPathElement> {
    val context = this

    return buildList {
        context.fold(this) { acc, element ->
            if (element is ValidationPathElement) acc.add(element)
            acc
        }
    }
}
