package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.StringViolationFactory

public open class StringIsNumericRule(
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationFactory.Default.isNumeric(
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        name: String,
    ) : this(
        violationGenerator = { value ->
            StringViolationFactory.Default.isNumeric(
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.all { it.isDigit() },
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringIsNumericRule(
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationFactory.Default.isNumeric(
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.all { it.isDigit() },
        ) {
            violationGenerator(value)
        }
    }
}
