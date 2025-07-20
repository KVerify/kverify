package io.github.kverify.violation.factory.string

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.factory.NamedViolationFactory
import io.github.kverify.violation.factory.ViolationFactory
import io.github.kverify.violation.set.string.StringContainsViolation

public open class StringContainsViolationFactory(
    public val subString: String,
    public val ignoreCase: Boolean = false,
) : ViolationFactory<String> {
    public constructor(
        char: Char,
        ignoreCase: Boolean = false,
    ) : this(
        subString = char.toString(),
        ignoreCase = ignoreCase,
    )

    override fun createViolation(value: String): Violation =
        StringContainsViolation(
            value = value,
            subString = subString,
            ignoreCase = ignoreCase,
        )
}

public open class NamedStringContainsViolationFactory(
    public val subString: String,
    public val ignoreCase: Boolean = false,
) : NamedViolationFactory<String> {
    override fun createViolation(value: NamedValue<String>): Violation =
        StringContainsViolation(
            value = value.value,
            subString = subString,
            ignoreCase = ignoreCase,
            name = value.name,
        )
}
