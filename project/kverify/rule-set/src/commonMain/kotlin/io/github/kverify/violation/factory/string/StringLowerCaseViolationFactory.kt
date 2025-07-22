package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringLowerCaseViolation

public open class StringIsLowerCaseViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation = StringLowerCaseViolation(value = value)
}

public open class NamedStringIsLowerCaseViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringLowerCaseViolation(
            value = value.value,
            name = value.name,
        )
}
