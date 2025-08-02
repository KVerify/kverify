package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringContainsRegexViolationFactory(
    public val regex: Regex,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.containsRegex(
            value = value.value,
            regex = regex,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsRegexViolationFactory(
    stringRegex: String,
    stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
): NamedStringContainsRegexViolationFactory =
    NamedStringContainsRegexViolationFactory(
        regex = stringRegex.toRegex(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsRegexViolationFactory(stringRegex: String): NamedStringContainsRegexViolationFactory =
    NamedStringContainsRegexViolationFactory(
        regex = stringRegex.toRegex(),
    )
