package io.github.kverify.named.check

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmInline

/**
 * A [ViolationFactory] producing violations for [NamedValue] instances.
 */
public typealias NamedViolationFactory<T> = ViolationFactory<NamedValue<T>>

/**
 * Wraps this [ViolationFactory] to operate on [NamedValue] values.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ViolationFactory<T>.asNamedViolationFactory(): NamedViolationFactory<T> =
    NamedViolationFactoryAdapter(
        baseFactory = this,
    )

/**
 * Wraps [baseFactory] to create violations for [NamedValue] instances.
 *
 * @see ViolationFactory
 */
@JvmInline
public value class NamedViolationFactoryAdapter<T>(
    public val baseFactory: ViolationFactory<T>,
) : NamedViolationFactory<T> {
    /**
     * Extracts the value from [NamedValue] and delegates to [baseFactory].
     */
    override fun createViolation(value: NamedValue<T>): Violation = baseFactory.createViolation(value.value)
}
