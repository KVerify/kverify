package io.github.kverify.violation.factory.string

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringEndsWithViolation

public open class StringEndsWithViolationFactory(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
) : ViolationFactory<String> {
    override fun createViolation(value: String): Violation =
        StringEndsWithViolation(
            value = value,
            suffix = suffix,
            ignoreCase = ignoreCase,
        )
}

public open class NamedStringEndsWithViolationFactory(
    public val suffix: String,
    public val ignoreCase: Boolean = false,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringEndsWithViolation(
            value = value.value,
            suffix = suffix,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}
