package io.github.kverify.core.scope.decorator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.element.ValidationPathElement
import io.github.kverify.core.scope.ValidationScope
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class IndexValidationScope<out T : ValidationScope>(
    validationContext: ValidationContext,
    index: Int,
    public override val originalValidationScope: T,
) : ValidationScopeDecorator<T> {
    override val validationContext: ValidationContext = validationContext + ValidationPathElement.index(index)
}

public inline fun <T : ValidationScope> T.index(
    index: Int,
    block: IndexValidationScope<T>.() -> Unit,
): IndexValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return IndexValidationScope(
        validationContext = validationContext,
        index = index,
        originalValidationScope = this,
    ).apply(block)
}

public inline fun <T : ValidationScope> ValidationScopeDecorator<T>.index(
    index: Int,
    block: IndexValidationScope<T>.() -> Unit,
): IndexValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return IndexValidationScope(
        validationContext = validationContext,
        index = index,
        originalValidationScope = originalValidationScope,
    ).apply(block)
}
