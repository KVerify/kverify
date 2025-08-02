package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringMatchesViolationFactory(
    public val regex: Regex,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.matches(
            value = value.value,
            regex = regex,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringMatchesViolationFactory(
    stringRegex: String,
    stringViolationProvider: StringViolationProvider,
): NamedStringMatchesViolationFactory =
    NamedStringMatchesViolationFactory(
        regex = stringRegex.toRegex(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringMatchesViolationFactory(stringRegex: String): NamedStringMatchesViolationFactory =
    NamedStringMatchesViolationFactory(
        regex = stringRegex.toRegex(),
    )
