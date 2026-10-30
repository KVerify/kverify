package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathIndex
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

public interface ValueValidationScope<out V> : ValidationScope {
    public val value: V

    override fun ValidationContext.validationScope(): ValueValidationScope<V>
}

public fun <V : Any> ValueValidationScope<V?>.takeIfValueNotNull(): ValueValidationScope<V>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueValidationScope<V>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>> ValueValidationScope<I>.each(block: ValueValidationScope<V>.() -> Unit) {
    value.forEachIndexed { index, element ->
        pathIndex(index).validationScope().forValue(element).block()
    }
}

public fun <V> ValueValidationScope(
    value: V,
    validationStrategy: ValidationStrategy,
    validationContext: ValidationContext,
): ValueValidationScope<V> =
    ValueValidationScopeImpl(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

private class ValueValidationScopeImpl<V>(
    override val value: V,
    override val validationStrategy: ValidationStrategy,
    override val validationContext: ValidationContext,
) : ValueValidationScope<V> {
    override fun ValidationContext.validationScope(): ValueValidationScopeImpl<V> =
        ValueValidationScopeImpl(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext + this,
        )

    override fun <V> forValue(value: V): ValueValidationScope<V> =
        ValueValidationScopeImpl(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext,
        )

    override fun <V> forProperty(property: KProperty0<V>): ValueValidationScope<V> =
        ValueValidationScopeImpl(
            value = property.get(),
            validationStrategy = validationStrategy,
            validationContext = validationContext.pathName(property.name),
        )
}
