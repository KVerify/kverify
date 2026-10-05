package io.github.kverify.core.scope

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.jvm.JvmName
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

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verifyValue(value: V): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

@JvmName("verifyValueExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verifyValue(value: V): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = this,
    )

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verifyProperty(property: KProperty0<V>): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = validationContext + NamePathElement(property.name),
    )

@JvmName("verifyPropertyExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verifyProperty(property: KProperty0<V>): ValueVerificationScope<V, S> =
    ValueVerificationScope(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = this + NamePathElement(property.name),
    )

public fun <V : Any, S : ValidationStrategy> ValueVerificationScope<V?, S>.takeIfNotNull(): ValueVerificationScope<V, S>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueVerificationScope<V, S>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>, S : ValidationStrategy> ValueVerificationScope<I, S>.each(
    block: context(S, ValidationContext) ValueVerificationScope<V, S>.() -> Unit,
) {
    value.forEachIndexed { index, element ->
        val newContext = validationContext + IndexPathElement(index)

        val verification =
            ValueVerificationScope(
                value = element,
                validationStrategy = validationStrategy,
                validationContext = newContext,
            )

        context(validationStrategy, newContext) { verification.block() }
    }
}
