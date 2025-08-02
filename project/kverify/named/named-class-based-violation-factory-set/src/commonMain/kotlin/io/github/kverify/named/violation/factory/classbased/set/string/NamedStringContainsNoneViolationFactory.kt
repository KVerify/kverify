package io.github.kverify.named.violation.factory.classbased.set.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.set.provider.StringViolationProvider

public class NamedStringContainsNoneViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        stringViolationProvider.containsNone(
            value = value.value,
            chars = chars,
            name = value.name,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsNoneViolationFactory(
    vararg chars: Char,
    stringViolationProvider: StringViolationProvider,
): NamedStringContainsNoneViolationFactory =
    NamedStringContainsNoneViolationFactory(
        chars = chars.toSet(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsNoneViolationFactory(vararg chars: Char): NamedStringContainsNoneViolationFactory =
    NamedStringContainsNoneViolationFactory(
        chars = chars.toSet(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsNoneViolationFactory(
    string: String,
    stringViolationProvider: StringViolationProvider,
): NamedStringContainsNoneViolationFactory =
    NamedStringContainsNoneViolationFactory(
        chars = string.asIterable(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun NamedStringContainsNoneViolationFactory(string: String): NamedStringContainsNoneViolationFactory =
    NamedStringContainsNoneViolationFactory(
        chars = string.asIterable(),
    )
