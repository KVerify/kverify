package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringMatchesViolationFactory(
    public val regex: Regex,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.matches(
            value = value,
            regex = regex,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesViolationFactory(
    stringRegex: String,
    stringViolationProvider: StringViolationProvider,
): StringMatchesViolationFactory =
    StringMatchesViolationFactory(
        regex = stringRegex.toRegex(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringMatchesViolationFactory(stringRegex: String): StringMatchesViolationFactory =
    StringMatchesViolationFactory(
        regex = stringRegex.toRegex(),
    )
