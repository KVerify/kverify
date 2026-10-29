package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathIndex
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

public interface ValueValidationScope<out V, out S : ValidationStrategy> : ValidationScope<S> {
    public val value: V
}

public fun <V, S : ValidationStrategy> ValidationScope<S>.verifyValue(value: V): ValueValidationScope<V, S> =
    ValueValidationScope(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

context(validationScope: ValidationScope<S>)
public fun <V, S : ValidationStrategy> ValidationContext.verifyValue(value: V): ValueValidationScope<V, S> =
    ValueValidationScope(
        value = value,
        validationStrategy = validationScope.validationStrategy,
        validationContext = validationScope.validationContext + this,
    )

public fun <V, S : ValidationStrategy> ValidationScope<S>.verifyProperty(property: KProperty0<V>): ValueValidationScope<V, S> =
    ValueValidationScope(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = validationContext.pathName(property.name),
    )

context(validationScope: ValidationScope<S>)
public fun <V, S : ValidationStrategy> ValidationContext.verifyProperty(property: KProperty0<V>): ValueValidationScope<V, S> =
    ValueValidationScope(
        value = property.get(),
        validationStrategy = validationScope.validationStrategy,
        validationContext = validationScope.validationContext + this.pathName(property.name),
    )

public fun <V : Any, S : ValidationStrategy> ValueValidationScope<V?, S>.takeIfNotNull(): ValueValidationScope<V, S>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueValidationScope<V, S>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>, S : ValidationStrategy> ValueValidationScope<I, S>.each(
    block: ValueValidationScope<V, S>.() -> Unit,
) {
    value.forEachIndexed { index, element ->
        ValueValidationScope(
            value = element,
            validationStrategy = validationStrategy,
            validationContext = validationContext.pathIndex(index),
        ).block()
    }
}

public fun <V, S : ValidationStrategy> ValueValidationScope(
    value: V,
    validationStrategy: S,
    validationContext: ValidationContext,
): ValueValidationScope<V, S> =
    ValueValidationScopeImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

private class ValueValidationScopeImpl<out V, out S : ValidationStrategy>(
    override val value: V,
    override val validationStrategy: S,
    override val validationContext: ValidationContext,
) : ValueValidationScope<V, S>
