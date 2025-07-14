package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.StringViolationFactory

public open class StringMaxLengthRule(
    public val max: Int,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationFactory.Default.maxLength(
            max = max,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        max: Int,
        name: String,
    ) : this(
        max = max,
        violationGenerator = { value ->
            StringViolationFactory.Default.maxLength(
                max = max,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.length <= max,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringMaxLengthRule(
    public val max: Int,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationFactory.Default.maxLength(
            max = max,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.length <= max,
        ) {
            violationGenerator(value)
        }
    }
}
