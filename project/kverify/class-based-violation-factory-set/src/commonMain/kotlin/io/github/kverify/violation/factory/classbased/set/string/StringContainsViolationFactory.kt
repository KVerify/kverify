package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringContainsViolationFactory(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.contains(
            value = value,
            substring = substring,
            ignoreCase = ignoreCase,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsViolationFactory(
    char: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): StringContainsViolationFactory =
    StringContainsViolationFactory(
        substring = char.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsViolationFactory(
    char: Char,
    ignoreCase: Boolean = false,
): StringContainsViolationFactory =
    StringContainsViolationFactory(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
