package io.github.kverify.core

import kotlin.contracts.ExperimentalExtendedContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KProperty0

@DslMarker
public annotation class KverifyDsl

@KverifyDsl
public interface ValidationScope {
    public val validationStrategy: ValidationStrategy

    public val validationContext: ValidationContext

    public operator fun plus(otherValidationContext: ValidationContext): ValidationScope

    public fun <V> forValue(value: V): ValueValidationScope<V>
}

public interface ValueValidationScope<out V> : ValidationScope {
    public val value: V

    override fun plus(otherValidationContext: ValidationContext): ValueValidationScope<V>
}

private class ValidationScopeImpl(
    override val validationStrategy: ValidationStrategy,
    override val validationContext: ValidationContext,
) : ValidationScope {
    override fun plus(otherValidationContext: ValidationContext): ValidationScope =
        ValidationScopeImpl(
            validationStrategy = validationStrategy,
            validationContext = validationContext + otherValidationContext,
        )

    override fun <V> forValue(value: V): ValueValidationScope<V> =
        ValueValidationScope(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext,
        )
}

private class ValueValidationScopeImpl<V>(
    override val value: V,
    override val validationStrategy: ValidationStrategy,
    override val validationContext: ValidationContext,
) : ValueValidationScope<V> {
    override fun plus(otherValidationContext: ValidationContext): ValueValidationScope<V> =
        ValueValidationScopeImpl(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext + otherValidationContext,
        )

    override fun <V> forValue(value: V): ValueValidationScope<V> =
        ValueValidationScopeImpl(
            value = value,
            validationStrategy = validationStrategy,
            validationContext = validationContext,
        )
}

/**
 * Returns a [ValueValidationScope] for the value of [property], with the property name
 * added to this scope as a [pathName].
 */
public fun <V> ValidationScope.forProperty(property: KProperty0<V>): ValueValidationScope<V> =
    plus(
        pathName(property.name),
    ).forValue(
        property.get(),
    )

/**
 * Reports the violation from [lazyViolation] if [condition] is `true`.
 */
@OptIn(ExperimentalExtendedContracts::class)
public inline fun ValidationScope.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    contract {
        callsInPlace(lazyViolation, InvocationKind.AT_MOST_ONCE)
        condition.holdsIn(lazyViolation)
    }

    if (condition) {
        val violation = lazyViolation()

        validationStrategy.failWith(violation)
    }
}

/**
 * Passes [violation] to the [ValidationScope.validationStrategy] of this scope.
 */
public fun ValidationScope.failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

/**
 * Returns this scope with a non-null value type if its [value] is not `null`, or `null` otherwise.
 */
public fun <V : Any> ValueValidationScope<V?>.takeIfValueNotNull(): ValueValidationScope<V>? =
    if (value != null) {
        @Suppress("UNCHECKED_CAST")
        this as ValueValidationScope<V>
    } else {
        null
    }

/**
 * Iterates over the [value] and calls [block] on a new [ValueValidationScope] for each element.
 * The scope of each element is this scope with the index of the element added as a [pathIndex].
 */
public inline fun <V> ValueValidationScope<Iterable<V>>.each(block: ValueValidationScope<V>.() -> Unit) {
    value.forEachIndexed { index, element ->
        plus(pathIndex(index)).forValue(element).block()
    }
}

/**
 * Returns a [ValidationScope] with the given [validationStrategy] and [validationContext].
 */
public fun ValidationScope(
    validationStrategy: ValidationStrategy,
    validationContext: ValidationContext,
): ValidationScope =
    ValidationScopeImpl(
        validationStrategy = validationStrategy,
        validationContext = validationContext,
    )

/**
 * Returns a [ValueValidationScope] with the given [value], [validationStrategy] and
 * [validationContext].
 */
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
