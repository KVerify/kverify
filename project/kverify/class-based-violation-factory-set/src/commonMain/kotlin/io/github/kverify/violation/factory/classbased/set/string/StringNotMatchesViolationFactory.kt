package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringNotMatchesViolationFactory(
    public val regex: Regex,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.notMatches(
            value = value,
            regex = regex,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotMatchesViolationFactory(
    stringRegex: String,
    stringViolationProvider: StringViolationProvider,
): StringNotMatchesViolationFactory =
    StringNotMatchesViolationFactory(
        regex = stringRegex.toRegex(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringNotMatchesViolationFactory(stringRegex: String): StringNotMatchesViolationFactory =
    StringNotMatchesViolationFactory(
        regex = stringRegex.toRegex(),
    )
