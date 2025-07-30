package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNotEmptyViolation

public class StringIsNotEmptyViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringNotEmptyViolation(
            value = value,
        )
}

public class NamedStringIsNotEmptyViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringNotEmptyViolation(
            value = value.value,
            name = value.name,
        )
}
