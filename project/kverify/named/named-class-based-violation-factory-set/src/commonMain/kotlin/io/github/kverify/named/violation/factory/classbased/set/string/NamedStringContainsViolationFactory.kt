package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringContainsViolationFactory(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.contains(
            value = value.value,
            substring = substring,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}
