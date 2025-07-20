package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringLengthBetweenViolation

public open class StringLengthBetweenViolationFactory(
    public val minLength: Int,
    public val maxLength: Int,
) : ViolationFactory<String> {
    public constructor(
        range: IntRange,
    ) : this(
        minLength = range.first,
        maxLength = range.last,
    )

    override fun createViolation(value: String): Violation =
        StringLengthBetweenViolation(
            value = value,
            minLength = minLength,
            maxLength = maxLength,
        )
}

public open class NamedStringLengthBetweenViolationFactory(
    public val minLength: Int,
    public val maxLength: Int,
) : NamedViolationFactory<String> {
    public constructor(
        range: IntRange,
    ) : this(
        minLength = range.first,
        maxLength = range.last,
    )

    override fun createViolation(value: NamedValue<String>): Violation =
        StringLengthBetweenViolation(
            value = value.value,
            minLength = minLength,
            maxLength = maxLength,
            name = value.name,
        )
}
