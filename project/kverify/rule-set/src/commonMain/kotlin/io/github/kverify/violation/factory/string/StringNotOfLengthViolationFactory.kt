package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNotOfLengthViolation

public class StringNotOfLengthViolationFactory(
    public val length: Int,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringNotOfLengthViolation(
            value = value,
            length = length,
        )
}

public class NamedStringNotOfLengthViolationFactory(
    public val length: Int,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringNotOfLengthViolation(
            value = value.value,
            length = length,
            name = value.name,
        )
}
