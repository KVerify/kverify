package io.github.kverify.core.util

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.ValidationPathElement

data class ExampleKey(
    val value: Any?,
) : ValidationContext.Key<ExampleElement> {
    override fun onCollision(
        oldElement: ValidationContext.Element,
        newElement: ValidationContext.Element,
    ): ValidationContext.Element {
        TODO("Not yet implemented")
    }

    companion object Key : ValidationContext.Key<ExampleElement> by ExampleKey(null)
}

val testValidationKeys: Array<ValidationContext.Key<*>> =
    arrayOf(
        ValidationPathElement,
        ExampleKey,
    )
