package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringStartsWithRule(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.startsWith(
            prefix = prefix,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        prefix: String,
        name: String,
        ignoreCase: Boolean = false,
    ) : this(
        prefix = prefix,
        ignoreCase = ignoreCase,
        violationGenerator = { value ->
            StringViolationProvider.Default.startsWith(
                prefix = prefix,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.startsWith(prefix, ignoreCase),
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringStartsWithRule(
    public val prefix: String,
    public val ignoreCase: Boolean = false,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.startsWith(
            prefix = prefix,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.startsWith(prefix, ignoreCase),
        ) {
            violationGenerator(value)
        }
    }
}
