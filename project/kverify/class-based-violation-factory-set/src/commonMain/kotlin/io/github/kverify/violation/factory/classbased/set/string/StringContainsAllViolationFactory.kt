package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringContainsAllViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.containsAll(
            value = value,
            chars = chars,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolationFactory(
    vararg chars: Char,
    stringViolationProvider: StringViolationProvider,
): StringContainsAllViolationFactory =
    StringContainsAllViolationFactory(
        chars = chars.toSet(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolationFactory(vararg chars: Char): StringContainsAllViolationFactory =
    StringContainsAllViolationFactory(
        chars = chars.toSet(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolationFactory(
    string: String,
    stringViolationProvider: StringViolationProvider,
): StringContainsAllViolationFactory =
    StringContainsAllViolationFactory(
        chars = string.asIterable(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsAllViolationFactory(string: String): StringContainsAllViolationFactory =
    StringContainsAllViolationFactory(
        chars = string.asIterable(),
    )
