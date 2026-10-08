package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

public interface ValueVerificationScope<out V, out S : ValidationStrategy> : VerificationScope<S> {
    public val value: V
}

private class ValueVerificationScopeImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : ValueVerificationScope<V, S>

public fun <V, S : ValidationStrategy> ValueVerificationScope(
    value: V,
    validationStrategy: S,
    validationContext: ValidationContext,
): ValueVerificationScope<V, S> =
    ValueVerificationScopeImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

public inline fun <V, S : ValidationStrategy> VerificationScope<S>.verifyValue(
    value: V,
    block: ValueVerificationScope<V, S>.() -> Unit = {},
): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    ).apply(block)

public inline fun <V, S : ValidationStrategy> VerificationScope<S>.verifyProperty(
    property: KProperty0<V>,
    block: ValueVerificationScope<V, S>.() -> Unit = {},
): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = validationContext + NamePathElement(property.name),
    ).apply(block)

context(verificationScope: VerificationScope<S>)
public inline fun <V, S : ValidationStrategy> ValidationContext.verifyValue(
    value: V,
    block: ValueVerificationScope<V, S>.() -> Unit = {},
): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = value,
        validationStrategy = verificationScope.validationStrategy,
        validationContext = this,
    ).apply(block)

context(verificationScope: VerificationScope<S>)
public inline fun <V, S : ValidationStrategy> ValidationContext.verifyProperty(
    property: KProperty0<V>,
    block: ValueVerificationScope<V, S>.() -> Unit = {},
): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = property.get(),
        validationStrategy = verificationScope.validationStrategy,
        validationContext = this + NamePathElement(property.name),
    ).apply(block)

public fun <V : Any, S : ValidationStrategy> ValueVerificationScope<V?, S>.takeIfNotNull(): ValueVerificationScope<V, S>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueVerificationScope<V, S>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>, S : ValidationStrategy> ValueVerificationScope<I, S>.each(
    block: ValueVerificationScope<V, S>.() -> Unit,
) {
    value.forEachIndexed { index, element ->
        ValueVerificationScope(
            value = element,
            validationStrategy = validationStrategy,
            validationContext = validationContext + IndexPathElement(index),
        ).block()
    }
}
