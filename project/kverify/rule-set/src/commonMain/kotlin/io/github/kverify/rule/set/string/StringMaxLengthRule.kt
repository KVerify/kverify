package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringMaxLengthRule(
    public val maxLength: Int,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.maxLength(
            maxLength = maxLength,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        max: Int,
        name: String,
    ) : this(
        maxLength = max,
        violationGenerator = { value ->
            StringViolationProvider.Default.maxLength(
                maxLength = max,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.length <= maxLength,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringMaxLengthRule(
    public val max: Int,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.maxLength(
            maxLength = max,
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
