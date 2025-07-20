package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringMinLengthViolation

public open class StringMinLengthViolationFactory(
    public val min: Int,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringMinLengthViolation(
            value = value,
            min = min,
        )
}

public open class NamedStringMinLengthViolationFactory(
    public val min: Int,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringMinLengthViolation(
            value = value.value,
            min = min,
            name = value.name,
        )
}
