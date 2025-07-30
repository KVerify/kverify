package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringNotContainsRegexRule(
    public val regex: Regex,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.notContainsRegex(
            regex = regex,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        regex: Regex,
        name: String,
    ) : this(
        regex = regex,
        violationGenerator = { value ->
            StringViolationProvider.Default.notContainsRegex(
                regex = regex,
                value = value,
                name = name,
            )
        },
    )

    public constructor(
        stringRegex: String,
        violationGenerator: ValueViolationGenerator<String> = { value ->
            StringViolationProvider.Default.notContainsRegex(
                regex = stringRegex.toRegex(),
                value = value,
            )
        },
    ) : this(
        regex = stringRegex.toRegex(),
        violationGenerator = violationGenerator,
    )

    public constructor(
        stringRegex: String,
        name: String,
    ) : this(
        regex = stringRegex.toRegex(),
        violationGenerator = { value ->
            StringViolationProvider.Default.notContainsRegex(
                regex = stringRegex.toRegex(),
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            !value.contains(regex),
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringNotContainsRegexRule(
    public val regex: Regex,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.notContainsRegex(
            regex = regex,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    public constructor(
        stringRegex: String,
        violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
            StringViolationProvider.Default.notContainsRegex(
                regex = stringRegex.toRegex(),
                value = value,
                name = name,
            )
        },
    ) : this(
        regex = stringRegex.toRegex(),
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            !value.value.contains(regex),
        ) {
            violationGenerator(value)
        }
    }
}
