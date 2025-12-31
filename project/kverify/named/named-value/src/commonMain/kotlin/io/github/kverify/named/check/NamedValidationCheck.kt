package io.github.kverify.named.check

import io.github.kverify.core.rule.predicate.check.ValidationCheck
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmInline

/**
 * A [ValidationCheck] operating on a [NamedValue].
 */
public typealias NamedValidationCheck<T> = ValidationCheck<NamedValue<T>>

/**
 * Wraps this [ValidationCheck] to operate on [NamedValue] instances.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheck<T>.asNamedValidationCheck(): NamedValidationCheck<T> =
    NamedValidationCheckAdapter(
        baseCheck = this,
    )

/**
 * Wraps [baseCheck] to validate [NamedValue] instances.
 *
 * @see ValidationCheck
 */
@JvmInline
public value class NamedValidationCheckAdapter<in T>(
    public val baseCheck: ValidationCheck<T>,
) : NamedValidationCheck<T> {
    /**
     * Extracts the value from [NamedValue] and delegates to [baseCheck].
     */
    override fun isValid(value: NamedValue<T>): Boolean = baseCheck.isValid(value.value)
}
