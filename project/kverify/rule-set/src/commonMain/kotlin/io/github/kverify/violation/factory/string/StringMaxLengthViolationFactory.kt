package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringMaxLengthViolation

public open class StringMaxLengthViolationFactory(
    public val max: Int,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringMaxLengthViolation(
            value = value,
            max = max,
        )
}

public open class NamedStringMaxLengthViolationFactory(
    public val max: Int,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringMaxLengthViolation(
            value = value.value,
            max = max,
            name = value.name,
        )
}
