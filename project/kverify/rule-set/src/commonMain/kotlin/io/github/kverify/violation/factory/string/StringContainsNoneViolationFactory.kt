package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringContainsNoneViolation

public open class StringContainsNoneViolationFactory(
    public val chars: Iterable<Char>,
) : ViolationFactory<String> {
    public constructor(
        vararg chars: Char,
    ) : this(
        chars = chars.asList(),
    )

    public constructor(
        forbidden: String,
    ) : this(
        chars = forbidden.asIterable(),
    )

    override fun createViolation(value: String): Violation =
        StringContainsNoneViolation(
            value = value,
            chars = chars,
        )
}

public open class NamedStringContainsNoneViolationFactory(
    public val chars: Iterable<Char>,
) : NamedViolationFactory<String> {
    public constructor(
        vararg chars: Char,
    ) : this(
        chars = chars.asList(),
    )

    public constructor(
        forbidden: String,
    ) : this(
        chars = forbidden.asIterable(),
    )

    override fun createViolation(value: NamedValue<String>): Violation =
        StringContainsNoneViolation(
            value = value.value,
            chars = chars,
            name = value.name,
        )
}
