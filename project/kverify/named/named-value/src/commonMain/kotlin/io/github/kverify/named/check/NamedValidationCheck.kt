package io.github.kverify.named.check

import io.github.kverify.core.check.ValidationCheck
import io.github.kverify.named.model.NamedValue
import kotlin.jvm.JvmInline

public typealias NamedValidationCheck<T> = ValidationCheck<NamedValue<T>>

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ValidationCheck<T>.asNamedValidationCheck(): NamedValidationCheck<T> =
    NamedValidationCheckAdapter(
        baseCheck = this,
    )

@PublishedApi
@JvmInline
internal value class NamedValidationCheckAdapter<in T>(
    val baseCheck: ValidationCheck<T>,
) : NamedValidationCheck<T> {
    override fun isValid(value: NamedValue<T>): Boolean = baseCheck.isValid(value.value)
}
