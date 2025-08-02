package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringEndsWithViolationFactory(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.endsWith(
            value = value.value,
            suffix = suffix,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringEndsWithViolationFactory(
    suffix: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): NamedStringEndsWithViolationFactory =
    NamedStringEndsWithViolationFactory(
        suffix = suffix.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringEndsWithViolationFactory(
    suffix: Char,
    ignoreCase: Boolean = false,
): NamedStringEndsWithViolationFactory =
    NamedStringEndsWithViolationFactory(
        suffix = suffix.toString(),
        ignoreCase = ignoreCase,
    )
