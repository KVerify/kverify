package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringIsAlphabeticViolation

public open class StringIsAlphabeticViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringIsAlphabeticViolation(
            value = value,
        )
}

public open class NamedStringIsAlphabeticViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringIsAlphabeticViolation(
            value = value.value,
            name = value.name,
        )
}
