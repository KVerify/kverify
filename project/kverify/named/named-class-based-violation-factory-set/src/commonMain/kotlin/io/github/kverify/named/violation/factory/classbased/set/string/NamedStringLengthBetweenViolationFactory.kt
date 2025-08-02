package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringLengthBetweenViolationFactory(
    public val lengthRange: IntRange,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.lengthBetween(
            value = value.value,
            lengthRange = lengthRange,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringLengthBetweenViolationFactory(
    minLength: Int,
    maxLength: Int,
    stringViolationProvider: StringViolationProvider,
): NamedStringLengthBetweenViolationFactory =
    NamedStringLengthBetweenViolationFactory(
        lengthRange = minLength..maxLength,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringLengthBetweenViolationFactory(
    minLength: Int,
    maxLength: Int,
): NamedStringLengthBetweenViolationFactory =
    NamedStringLengthBetweenViolationFactory(
        lengthRange = minLength..maxLength,
    )
