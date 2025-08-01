package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringContainsOnlyViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.containsOnly(
            value = value,
            chars = chars,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyViolationFactory(
    vararg chars: Char,
    stringViolationProvider: StringViolationProvider,
): StringContainsOnlyViolationFactory =
    StringContainsOnlyViolationFactory(
        chars = chars.toSet(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyViolationFactory(vararg chars: Char): StringContainsOnlyViolationFactory =
    StringContainsOnlyViolationFactory(
        chars = chars.toSet(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyViolationFactory(
    string: String,
    stringViolationProvider: StringViolationProvider,
): StringContainsOnlyViolationFactory =
    StringContainsOnlyViolationFactory(
        chars = string.asIterable(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsOnlyViolationFactory(string: String): StringContainsOnlyViolationFactory =
    StringContainsOnlyViolationFactory(
        chars = string.asIterable(),
    )
