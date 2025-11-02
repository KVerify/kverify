package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringNumericViolationFactory(
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.numeric(
            value = value,
        )
}
