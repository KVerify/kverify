package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.rule.Rule
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class ThrowingValidationScope(
    public override val validationContext: ValidationContext,
) : ValidationScope {
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        throw ViolationException(violation)
    }
}

public inline fun <T> verifyThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationScope(validationContext).run(block)
}
