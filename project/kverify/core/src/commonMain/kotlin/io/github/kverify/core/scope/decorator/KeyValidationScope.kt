package io.github.kverify.core.scope.decorator

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.PropertyPathSegment
import io.github.kverify.core.scope.ValidationScope
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class KeyValidationScope<out T : ValidationScope>(
    validationContext: ValidationContext,
    key: String,
    public override val originalValidationScope: T,
) : ValidationScopeDecorator<T> {
    override val validationContext: ValidationContext =
        validationContext.derive(
            PropertyPathSegment(key),
        )
}

public inline fun <T : ValidationScope> T.key(
    key: String,
    block: KeyValidationScope<T>.() -> Unit,
): KeyValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return KeyValidationScope(
        validationContext,
        key = key,
        originalValidationScope = this,
    ).apply(block)
}

public inline fun <T : ValidationScope> ValidationScopeDecorator<T>.key(
    key: String,
    block: KeyValidationScope<T>.() -> Unit,
): KeyValidationScope<T> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return KeyValidationScope(
        validationContext,
        key = key,
        originalValidationScope = originalValidationScope,
    ).apply(block)
}
