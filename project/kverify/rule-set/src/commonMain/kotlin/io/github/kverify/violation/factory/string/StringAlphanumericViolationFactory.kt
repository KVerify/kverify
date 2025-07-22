package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringAlphanumericViolation

public open class StringIsAlphanumericViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringAlphanumericViolation(
            value = value,
        )
}

public open class NamedStringIsAlphanumericViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringAlphanumericViolation(
            value = value.value,
            name = value.name,
        )
}
