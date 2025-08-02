package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringContainsAllViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.containsAll(
            value = value.value,
            chars = chars,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllViolationFactory(
    vararg chars: Char,
    stringViolationProvider: StringViolationProvider,
): NamedStringContainsAllViolationFactory =
    NamedStringContainsAllViolationFactory(
        chars = chars.toSet(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllViolationFactory(vararg chars: Char): NamedStringContainsAllViolationFactory =
    NamedStringContainsAllViolationFactory(
        chars = chars.toSet(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllViolationFactory(
    string: String,
    stringViolationProvider: StringViolationProvider,
): NamedStringContainsAllViolationFactory =
    NamedStringContainsAllViolationFactory(
        chars = string.asIterable(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsAllViolationFactory(string: String): NamedStringContainsAllViolationFactory =
    NamedStringContainsAllViolationFactory(
        chars = string.asIterable(),
    )
