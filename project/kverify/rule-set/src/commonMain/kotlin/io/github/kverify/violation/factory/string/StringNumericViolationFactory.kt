package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringNumericViolation

public class StringIsNumericViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringNumericViolation(
            value = value,
        )
}

public class NamedStringIsNumericViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringNumericViolation(
            value = value.value,
            name = value.name,
        )
}
