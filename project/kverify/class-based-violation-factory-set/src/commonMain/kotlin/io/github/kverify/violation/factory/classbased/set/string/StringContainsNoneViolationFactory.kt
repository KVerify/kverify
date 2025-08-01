package io.github.kverify.violation.factory.classbased.set.string

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.provider.StringViolationProvider

public class StringContainsNoneViolationFactory(
    public val chars: Iterable<Char>,
    public val stringViolationProvider: StringViolationProvider = StringViolationProvider.Default,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        stringViolationProvider.containsNone(
            value = value,
            chars = chars,
        )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolationFactory(
    vararg chars: Char,
    stringViolationProvider: StringViolationProvider,
): StringContainsNoneViolationFactory =
    StringContainsNoneViolationFactory(
        chars = chars.toSet(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolationFactory(vararg chars: Char): StringContainsNoneViolationFactory =
    StringContainsNoneViolationFactory(
        chars = chars.toSet(),
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolationFactory(
    string: String,
    stringViolationProvider: StringViolationProvider,
): StringContainsNoneViolationFactory =
    StringContainsNoneViolationFactory(
        chars = string.asIterable(),
        stringViolationProvider = stringViolationProvider,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun StringContainsNoneViolationFactory(string: String): StringContainsNoneViolationFactory =
    StringContainsNoneViolationFactory(
        chars = string.asIterable(),
    )
