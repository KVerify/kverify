package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringNotContainsViolationFactory(
    public val substring: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.notContains(
            value = value.value,
            substring = substring,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringNotContainsViolationFactory(
    char: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): NamedStringNotContainsViolationFactory =
    NamedStringNotContainsViolationFactory(
        substring = char.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringNotContainsViolationFactory(
    char: Char,
    ignoreCase: Boolean = false,
): NamedStringNotContainsViolationFactory =
    NamedStringNotContainsViolationFactory(
        substring = char.toString(),
        ignoreCase = ignoreCase,
    )
