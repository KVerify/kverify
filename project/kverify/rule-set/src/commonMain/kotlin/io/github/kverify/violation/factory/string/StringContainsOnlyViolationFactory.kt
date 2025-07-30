package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringContainsOnlyViolation

public open class StringContainsOnlyViolationFactory(
    public val chars: Iterable<Char>,
) : ViolationFactory<String> {
    public constructor(
        vararg chars: Char,
    ) : this(
        chars = chars.asList(),
    )

    public constructor(
        string: String,
    ) : this(
        chars = string.asIterable(),
    )

    override fun createViolation(value: String): Violation =
        StringContainsOnlyViolation(
            value = value,
            chars = chars,
        )
}

public open class NamedStringContainsOnlyViolationFactory(
    public val chars: Iterable<Char>,
) : NamedViolationFactory<String> {
    public constructor(
        vararg chars: Char,
    ) : this(
        chars = chars.asList(),
    )

    public constructor(
        string: String,
    ) : this(
        chars = string.asIterable(),
    )

    override fun createViolation(value: NamedValue<String>): Violation =
        StringContainsOnlyViolation(
            value = value.value,
            chars = chars,
            name = value.name,
        )
}
