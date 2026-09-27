package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.model.NamePathElement
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

@KverifyDsl
public interface ValueVerification<out V, out S : ValidationStrategy> : Verification<S> {
    public val value: V
}

private class ValueVerificationImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationPath: ValidationPath,
) : ValueVerification<V, S>

public fun <V, S : ValidationStrategy> ValueVerification(
    value: V,
    validationStrategy: S,
    validationPath: ValidationPath,
): ValueVerification<V, S> =
    ValueVerificationImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationPath = validationPath,
    )

context(validationStrategy: S)
public inline fun <V, S : ValidationStrategy> ValidationPath.verifyValue(
    value: V,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
) {
    val valueVerification =
        ValueVerification(
            value = value,
            validationStrategy = validationStrategy,
            validationPath = this,
        )

    context(validationStrategy, this) { valueVerification.block() }
}

context(validationStrategy: S, validationPath: ValidationPath)
public inline fun <V, S : ValidationStrategy> verifyValue(
    value: V,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
): Unit = validationPath.verifyValue(value, block)

context(validationStrategy: S)
public inline fun <V, S : ValidationStrategy> ValidationPath.verifyProperty(
    property: KProperty0<V>,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
) {
    val newPath = this + NamePathElement(property.name)

    val valueVerification =
        ValueVerification(
            value = property.get(),
            validationStrategy = validationStrategy,
            validationPath = newPath,
        )

    context(validationStrategy, newPath) { valueVerification.block() }
}

context(validationStrategy: S, validationPath: ValidationPath)
public inline fun <V, S : ValidationStrategy> verifyProperty(
    property: KProperty0<V>,
    block: context(S, ValidationPath) ValueVerification<V, S>.() -> Unit,
): Unit = validationPath.verifyProperty(property, block)
