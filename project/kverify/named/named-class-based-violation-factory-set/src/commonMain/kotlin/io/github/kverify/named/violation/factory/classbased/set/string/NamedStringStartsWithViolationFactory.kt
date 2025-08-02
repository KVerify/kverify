package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringStartsWithViolationFactory(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.startsWith(
            value = value.value,
            prefix = prefix,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringStartsWithViolationFactory(
    prefix: Char,
    ignoreCase: Boolean = false,
    stringViolationProvider: StringViolationProvider,
): NamedStringStartsWithViolationFactory =
    NamedStringStartsWithViolationFactory(
        prefix = prefix.toString(),
        ignoreCase = ignoreCase,
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringStartsWithViolationFactory(
    prefix: Char,
    ignoreCase: Boolean = false,
): NamedStringStartsWithViolationFactory =
    NamedStringStartsWithViolationFactory(
        prefix = prefix.toString(),
        ignoreCase = ignoreCase,
    )
