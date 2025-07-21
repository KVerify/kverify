package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringIsUpperCaseRule(
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.isUpperCase(
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        name: String,
    ) : this(
        violationGenerator = { value ->
            StringViolationProvider.Default.isUpperCase(
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.all { it.isUpperCase() },
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringIsUpperCaseRule(
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.isUpperCase(
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.all { it.isUpperCase() },
        ) {
            violationGenerator(value)
        }
    }
}
