package io.github.kverify.core.verification

import io.github.kverify.core.model.NamePathElement
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation

public interface Verification<out S : ValidationStrategy> {
    public val validationStrategy: S
    public val validationPath: ValidationPath
}

public inline fun <V, S : ValidationStrategy> Verification<S>.verifyValue(
    value: V,
    block: ValueVerification<V, S>.() -> Unit,
) {
    val valueVerification =
        ValueVerification(
            value = value,
            validationStrategy = validationStrategy,
            validationPath = validationPath,
        )

    valueVerification.block()
}

public fun Verification<*>.failWith(violation: Violation): Unit = validationStrategy.enforce(violation)
