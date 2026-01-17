package io.github.kverify.core.scope.decorator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.PropertyPathSegment
import io.github.kverify.core.scope.ValidationScope
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class PropertyValidationScope<out T : ValidationScope>(
    validationContext: ValidationContext,
    propertyName: String,
    public override val originalValidationScope: T,
) : ValidationScopeDecorator<T> {
    override val validationContext: ValidationContext =
        validationContext.derive(
            PropertyPathSegment(propertyName),
        )
}

public inline fun <T : ValidationScope> T.property(
    name: String,
    block: PropertyValidationScope<T>.() -> Unit,
): PropertyValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return PropertyValidationScope(
        validationContext,
        propertyName = name,
        originalValidationScope = this,
    ).apply(block)
}

public inline fun <T : ValidationScope> ValidationScopeDecorator<T>.property(
    name: String,
    block: PropertyValidationScope<T>.() -> Unit,
): PropertyValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return PropertyValidationScope(
        validationContext,
        propertyName = name,
        originalValidationScope = originalValidationScope,
    ).apply(block)
}
