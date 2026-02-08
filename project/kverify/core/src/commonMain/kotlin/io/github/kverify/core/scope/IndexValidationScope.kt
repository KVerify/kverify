package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.element.ValidationPathElement
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class IndexValidationScope<out T : ValidationScope>(
    validationContext: ValidationContext,
    index: Int,
    originalValidationScope: T,
) : ValidationScope by originalValidationScope {
    override val validationContext: ValidationContext = validationContext + ValidationPathElement.index(index)
}

public inline fun <T : ValidationScope> T.index(
    index: Int,
    block: IndexValidationScope<T>.() -> Unit = {},
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
