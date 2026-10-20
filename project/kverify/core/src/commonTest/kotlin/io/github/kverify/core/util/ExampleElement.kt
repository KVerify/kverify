package io.github.kverify.core.util

import io.github.kverify.core.context.ValidationContext

data class ExampleElement(
    override val key: ValidationContext.Key<ExampleElement>,
) : ValidationContext.Element {
    companion object : ValidationContext.Element by ExampleElement(ExampleKey)
}

val testValidationElements: Array<ValidationContext.Element> =
    arrayOf(
        ExampleElement,
    )
