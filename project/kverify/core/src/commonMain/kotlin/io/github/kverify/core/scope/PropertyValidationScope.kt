package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.element.ValidationPathElement
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class PropertyValidationScope<out T : ValidationScope>(
    validationContext: ValidationContext,
    propertyName: String,
    originalValidationScope: T,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext = validationContext + ValidationPathElement.property(propertyName)
}

public inline fun <T : ValidationScope> T.property(
    name: String,
    block: PropertyValidationScope<T>.() -> Unit = {},
): PropertyValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return PropertyValidationScope(
        validationContext = validationContext,
        propertyName = name,
        originalValidationScope = this,
    ).apply(block)
}
