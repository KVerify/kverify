package io.github.kverify.core.verification

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.model.NamePathElement
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.jvm.JvmName
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

@JvmName("verifyValueExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationPath.verifyValue(value: V): ValueVerification<V, S> =
    ValueVerification(
        value = value,
        validationStrategy = validationStrategy,
        validationPath = this,
    )

context(validationStrategy: S, validationPath: ValidationPath)
public fun <V, S : ValidationStrategy> verifyValue(value: V): ValueVerification<V, S> = validationPath.verifyValue(value)

@JvmName("verifyPropertyExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationPath.verifyProperty(property: KProperty0<V>): ValueVerification<V, S> =
    ValueVerification(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationPath = this + NamePathElement(property.name),
    )

context(validationStrategy: S, validationPath: ValidationPath)
public fun <V, S : ValidationStrategy> verifyProperty(property: KProperty0<V>): ValueVerification<V, S> =
    validationPath.verifyProperty(property)
