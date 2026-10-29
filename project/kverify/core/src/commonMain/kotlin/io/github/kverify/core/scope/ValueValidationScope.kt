package io.github.kverify.core.scope

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.pathIndex
import io.github.kverify.core.context.pathName
import io.github.kverify.core.strategy.ValidationStrategy
import kotlin.reflect.KProperty0

public interface ValueValidationScope<out V> : ValidationScope {
    public val value: V
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

public fun <V> ValidationScope.forValue(value: V): ValueValidationScope<V> =
    ValueValidationScope(
        value = value,
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

public fun <V> ValidationScope.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    ValueValidationScope(
        value = property.get(),
        validationStrategy = validationStrategy,
        validationContext = validationContext.pathName(property.name),
    )

context(validationScope: ValidationScope)
public fun <V> ValidationContext.forValue(value: V): ValueValidationScope<V> =
    ValueValidationScope(
        value = value,
        validationStrategy = validationScope.validationStrategy,
        validationContext = validationScope.validationContext + this,
    )

context(validationScope: ValidationScope)
public fun <V> ValidationContext.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    ValueValidationScope(
        value = property.get(),
        validationStrategy = validationScope.validationStrategy,
        validationContext = (validationScope.validationContext + this).pathName(property.name),
    )

public fun <V : Any> ValueValidationScope<V?>.takeIfValueNotNull(): ValueValidationScope<V>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueValidationScope<V>
    } else {
        null
    }

public inline fun <V, I : Iterable<V>> ValueValidationScope<I>.each(block: ValueValidationScope<V>.() -> Unit) {
    value.forEachIndexed { index, element ->
        pathIndex(index).forValue(element).block()
    }
}

private class ValueValidationScopeImpl<V>(
    override val value: V,
    override val validationStrategy: ValidationStrategy,
    override val validationContext: ValidationContext,
) : ValueValidationScope<V>
