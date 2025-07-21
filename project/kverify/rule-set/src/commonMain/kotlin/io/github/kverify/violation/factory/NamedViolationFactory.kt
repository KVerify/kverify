package io.github.kverify.violation.factory

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

public typealias NamedViolationFactory<T> = ViolationFactory<NamedValue<T>>

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> ViolationFactory<T>.asNamedViolationFactory(): NamedViolationFactory<T> =
    NamedViolationFactoryAdapter(
        baseFactory = this,
    )

@PublishedApi
@JvmInline
internal value class NamedViolationFactoryAdapter<T>(
    private val baseFactory: ViolationFactory<T>,
) : NamedViolationFactory<T> {
    override fun createViolation(value: NamedValue<T>): Violation = baseFactory.createViolation(value.value)
}
