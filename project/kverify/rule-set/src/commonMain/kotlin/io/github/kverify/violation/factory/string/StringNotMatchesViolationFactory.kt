package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNotMatchesViolation

public open class StringNotMatchesViolationFactory(
    public val regex: Regex,
) : ViolationFactory<String> {
    public constructor(
        pattern: String,
    ) : this(
        regex = pattern.toRegex(),
    )

    override fun createViolation(value: String): Violation =
        StringNotMatchesViolation(
            value = value,
            regex = regex,
        )
}

public open class NamedStringNotMatchesViolationFactory(
    public val regex: Regex,
) : NamedViolationFactory<String> {
    public constructor(
        pattern: String,
    ) : this(
        regex = pattern.toRegex(),
    )

    override fun createViolation(value: NamedValue<String>): Violation =
        StringNotMatchesViolation(
            value = value.value,
            regex = regex,
            name = value.name,
        )
}
