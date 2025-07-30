package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringStartsWithViolation

public open class StringStartsWithViolationFactory(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringStartsWithViolation(
            value = value,
            prefix = prefix,
            ignoreCase = ignoreCase,
        )
}

public open class NamedStringStartsWithViolationFactory(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringStartsWithViolation(
            value = value.value,
            prefix = prefix,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}
