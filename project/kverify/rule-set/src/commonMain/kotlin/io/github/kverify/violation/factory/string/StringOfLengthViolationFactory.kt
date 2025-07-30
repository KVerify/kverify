package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringOfLengthViolation

public class StringOfLengthViolationFactory(
    public val length: Int,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringOfLengthViolation(
            value = value,
            length = length,
        )
}

public class NamedStringOfLengthViolationFactory(
    public val length: Int,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringOfLengthViolation(
            value = value.value,
            length = length,
            name = value.name,
        )
}
