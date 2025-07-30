package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringAlphabeticViolation

public open class StringIsAlphabeticViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringAlphabeticViolation(
            value = value,
        )
}

public open class NamedStringIsAlphabeticViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringAlphabeticViolation(
            value = value.value,
            name = value.name,
        )
}
