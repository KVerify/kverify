package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringContainsRule(
    public val string: String,
    public val ignoreCase: Boolean = false,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.contains(
            string = string,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        string: String,
        ignoreCase: Boolean = false,
        name: String,
    ) : this(
        string = string,
        ignoreCase = ignoreCase,
        violationGenerator = { value ->
            StringViolationProvider.Default.contains(
                string = string,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.contains(string, ignoreCase),
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringContainsRule(
    public val string: String,
    public val ignoreCase: Boolean = false,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.contains(
            string = string,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.contains(string, ignoreCase),
        ) {
            violationGenerator(value)
        }
    }
}
