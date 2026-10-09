package io.github.kverify.core.scope

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathIndex
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.violation.Violation

@KverifyDsl
public interface VerificationScope<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationContext: ValidationContext
}

private class VerificationScopeImpl<out S : ValidationStrategy>(
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : VerificationScope<S>

public fun <S : ValidationStrategy> VerificationScope(
    validationStrategy: S,
    validationContext: ValidationContext,
): VerificationScope<S> =
    VerificationScopeImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

context(verificationScope: VerificationScope<S>)
public inline fun <S : ValidationStrategy> ValidationContext.verify(block: VerificationScope<S>.() -> Unit = {}): VerificationScope<S> =
    VerificationScope(
        validationStrategy = verificationScope.validationStrategy,
        validationContext = this,
    ).apply(block)

public fun <S : ValidationStrategy> VerificationScope<S>.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

public inline fun <S : ValidationStrategy> VerificationScope<S>.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit = validationStrategy.failIf(condition, lazyViolation)

public fun <S : ValidationStrategy> VerificationScope<S>.pathName(name: String): ValidationContext = validationContext.pathName(name)

public fun <S : ValidationStrategy> VerificationScope<S>.pathIndex(index: Int): ValidationContext = validationContext.pathIndex(index)
