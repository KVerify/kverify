package io.github.kverify.core.model

import io.github.kverify.core.annotation.KverifyDsl
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.jvm.JvmName
import kotlin.reflect.KProperty0

@KverifyDsl
public class Verification<out V, out S : ValidationStrategy>(
    public val value: V,
    public val validationStrategy: S,
    public val validationContext: ValidationContext,
)

@JvmName("extVerify")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verify(value: V): Verification<V, S> =
    Verification(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = this,
    )

@JvmName("extVerify")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verify(property: KProperty0<V>): Verification<V, S> =
    Verification(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = this + NamePathElement(property.name),
    )

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verify(value: V): Verification<V, S> = validationContext.verify(value)

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verify(property: KProperty0<V>): Verification<V, S> = validationContext.verify(property)

public inline fun <V, S : ValidationStrategy> Verification<V, S>.using(
    block: context(S, ValidationContext) Verification<V, S>.() -> Unit,
) {
    context(strategy, context) { block() }
    context(validationStrategy, validationContext) { block() }
}
