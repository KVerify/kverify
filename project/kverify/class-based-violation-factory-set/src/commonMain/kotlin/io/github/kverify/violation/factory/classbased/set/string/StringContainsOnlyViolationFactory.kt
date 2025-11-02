package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringContainsOnlyViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.containsOnly(
            value = value,
            chars = chars,
        )
}
