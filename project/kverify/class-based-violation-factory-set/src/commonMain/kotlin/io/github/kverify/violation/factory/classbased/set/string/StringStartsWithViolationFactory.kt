package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringStartsWithViolationFactory(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.startsWith(
            value = value,
            prefix = prefix,
            ignoreCase = ignoreCase,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringStartsWithViolationFactory(
    prefix: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): StringStartsWithViolationFactory =
    StringStartsWithViolationFactory(
        prefix = prefix.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringStartsWithViolationFactory(
    prefix: Char,
    ignoreCase: Boolean = false,
): StringStartsWithViolationFactory =
    StringStartsWithViolationFactory(
        prefix = prefix.toString(),
        ignoreCase = ignoreCase,
    )
