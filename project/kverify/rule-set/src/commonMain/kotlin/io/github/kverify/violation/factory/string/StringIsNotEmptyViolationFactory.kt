package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringIsNotEmptyViolation

public class StringIsNotEmptyViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringIsNotEmptyViolation(
            value = value,
        )
}

public class NamedStringIsNotEmptyViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringIsNotEmptyViolation(
            value = value.value,
            name = value.name,
        )
}
