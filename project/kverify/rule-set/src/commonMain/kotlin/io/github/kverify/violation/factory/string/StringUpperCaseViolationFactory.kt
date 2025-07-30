package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringUpperCaseViolation

public class StringIsUpperCaseViolationFactory : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringUpperCaseViolation(
            value = value,
        )
}

public class NamedStringIsUpperCaseViolationFactory : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringUpperCaseViolation(
            value = value.value,
            name = value.name,
        )
}
