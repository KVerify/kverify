package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringIsUpperCaseViolation

public class StringIsUpperCaseViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringIsUpperCaseViolation(
            value = value,
        )
}

public class NamedStringIsUpperCaseViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringIsUpperCaseViolation(
            value = value.value,
            name = value.name,
        )
}
