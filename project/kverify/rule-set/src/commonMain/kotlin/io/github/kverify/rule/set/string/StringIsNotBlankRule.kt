package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringIsNotBlankRule(
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.notBlank(
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        name: String,
    ) : this(
        violationGenerator = { value ->
            StringViolationProvider.Default.notBlank(
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.isNotBlank(),
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringIsNotBlankRule(
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.notBlank(
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.isNotBlank(),
        ) {
            violationGenerator(value)
        }
    }
}
