package io.github.kverify.core.verification

import io.github.kverify.core.context.IndexPathElement
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.jvm.JvmName
import kotlin.reflect.KProperty0

public interface ValueVerification<out V, out S : ValidationStrategy> : Verification<S> {
    public val value: V
}

private class ValueVerificationImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : ValueVerification<V, S>

public fun <V, S : ValidationStrategy> ValueVerification(
    value: V,
    validationStrategy: S,
    validationContext: ValidationContext,
): ValueVerification<V, S> =
    ValueVerificationImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verifyValue(value: V): ValueVerification<V, S> =
    ValueVerification(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

@JvmName("verifyValueExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verifyValue(value: V): ValueVerification<V, S> =
    ValueVerification(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = this,
    )

context(validationStrategy: S, validationContext: ValidationContext)
public fun <V, S : ValidationStrategy> verifyProperty(property: KProperty0<V>): ValueVerification<V, S> =
    ValueVerification(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = validationContext + NamePathElement(property.name),
    )

@JvmName("verifyPropertyExtension")
context(validationStrategy: S)
public fun <V, S : ValidationStrategy> ValidationContext.verifyProperty(property: KProperty0<V>): ValueVerification<V, S> =
    ValueVerification(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = this + NamePathElement(property.name),
    )

public fun <V : Any, S : ValidationStrategy> ValueVerification<V?, S>.takeIfNotNull(): ValueVerification<V, S>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueVerification<V, S>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>, S : ValidationStrategy> ValueVerification<I, S>.each(
    block: context(S, ValidationContext) ValueVerification<V, S>.() -> Unit,
) {
    value.forEachIndexed { index, element ->
        val newContext = validationContext + IndexPathElement(index)

        val verification =
            ValueVerification(
                value = element,
                validationStrategy = validationStrategy,
                validationContext = newContext,
            )

        context(validationStrategy, newContext) { verification.block() }
    }
}
