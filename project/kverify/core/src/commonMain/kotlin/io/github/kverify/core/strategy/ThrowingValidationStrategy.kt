package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationStrategy] that throws a [ViolationException] with the reported violation.
 */
public class ThrowingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation): Nothing = throw ViolationException(violation)
}

/**
 * Calls [block] in a [ValidationScope] with the given [validationContext] and returns
 * the result of [block].
 * A violation reported in that scope is thrown as a [ViolationException].
 */
public inline fun <R> validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ValidationScope.() -> R,
): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ValidationScope(
        validationStrategy = ThrowingValidationStrategy(),
        validationContext = validationContext,
    ).run(block)
}
