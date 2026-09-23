package io.github.kverify.core.model

import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

public class Verification<out V, out S : ValidationStrategy>(
    public val value: V,
    public val strategy: S,
    public val context: ValidationContext,
)

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verify(value: V): Verification<V, S> =
    Verification(
        value = value,
        strategy = validationStrategy,
        context = validationContext,
    )

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verify(property: KProperty0<V>): Verification<V, S> =
    Verification(
        value = property.get(),
        strategy = validationStrategy,
        context = validationContext + NamePathElement(property.name),
    )

public inline infix fun <V, S : ValidationStrategy> Verification<V, S>.with(
    block: context(S, ValidationContext) Verification<V, S>.() -> Unit,
) {
    val verification = this
    context(strategy, context) {
        verification.block()
    }
}
