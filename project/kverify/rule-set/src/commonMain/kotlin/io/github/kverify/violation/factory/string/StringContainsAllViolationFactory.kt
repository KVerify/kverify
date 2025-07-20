package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringContainsAllViolation

public open class StringContainsAllViolationFactory(
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
        StringContainsAllViolation(
            value = value,
            chars = chars,
        )
}

public open class NamedStringContainsAllViolationFactory(
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
        StringContainsAllViolation(
            value = value.value,
            chars = chars,
            name = value.name,
        )
}
