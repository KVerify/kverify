package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringEndsWithViolationFactory(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.endsWith(
            value = value,
            suffix = suffix,
            ignoreCase = ignoreCase,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringEndsWithViolationFactory(
    suffix: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): StringEndsWithViolationFactory =
    StringEndsWithViolationFactory(
        suffix = suffix.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringEndsWithViolationFactory(
    suffix: Char,
    ignoreCase: Boolean = false,
): StringEndsWithViolationFactory =
    StringEndsWithViolationFactory(
        suffix = suffix.toString(),
        ignoreCase = ignoreCase,
    )
