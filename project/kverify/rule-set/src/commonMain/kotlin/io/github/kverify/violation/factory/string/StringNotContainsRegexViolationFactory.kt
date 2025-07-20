package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNotContainsRegexViolation

public open class StringNotContainsRegexViolationFactory(
    public val regex: Regex,
) : ViolationFactory<String> {
    public constructor(
        pattern: String,
    ) : this(
        regex = pattern.toRegex(),
    )

    override fun createViolation(value: String): Violation =
        StringNotContainsRegexViolation(
            value = value,
            regex = regex,
        )
}

public open class NamedStringNotContainsRegexViolationFactory(
    public val regex: Regex,
) : NamedViolationFactory<String> {
    public constructor(
        pattern: String,
    ) : this(
        regex = pattern.toRegex(),
    )

    override fun createViolation(value: NamedValue<String>): Violation =
        StringNotContainsRegexViolation(
            value = value.value,
            regex = regex,
            name = value.name,
        )
}
