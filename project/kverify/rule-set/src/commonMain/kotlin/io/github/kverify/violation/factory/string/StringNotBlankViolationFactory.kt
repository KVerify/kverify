package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNotBlankViolation

public object StringIsNotBlankViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringNotBlankViolation(
            value = value,
        )
}

public object NamedStringIsNotBlankViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringNotBlankViolation(
            value = value.value,
            name = value.name,
        )
}
