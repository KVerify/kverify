package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringLengthNotBetweenViolationFactory(
    public val lengthRange: IntRange,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.lengthNotBetween(
            value = value,
            lengthRange = lengthRange,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringLengthNotBetweenViolationFactory(
    minLength: Int,
    maxLength: Int,
    stringViolationProvider: StringViolationProvider,
): StringLengthNotBetweenViolationFactory =
    StringLengthNotBetweenViolationFactory(
        lengthRange = minLength..maxLength,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringLengthNotBetweenViolationFactory(
    minLength: Int,
    maxLength: Int,
): StringLengthNotBetweenViolationFactory =
    StringLengthNotBetweenViolationFactory(
        lengthRange = minLength..maxLength,
    )
