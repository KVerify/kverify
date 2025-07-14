package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.StringViolationFactory

public open class StringNotOfLengthRule(
    public val length: Int,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationFactory.Default.notOfLength(
            length = length,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        length: Int,
        name: String,
    ) : this(
        length = length,
        violationGenerator = { value ->
            StringViolationFactory.Default.notOfLength(
                length = length,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.length != length,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringNotOfLengthRule(
    public val length: Int,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationFactory.Default.notOfLength(
            length = length,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.length != length,
        ) {
            violationGenerator(value)
        }
    }
}
