package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringLengthBetweenRule(
    public val range: IntRange,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.lengthBetween(
            value = value,
            range = range,
        )
    },
) : Rule<String> {
    public constructor(
        min: Int,
        max: Int,
        violationGenerator: ValueViolationGenerator<String> = { value ->
            StringViolationProvider.Default.lengthBetween(
                range = min..max,
                value = value,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    public constructor(
        range: IntRange,
        name: String,
    ) : this(
        range = range,
        violationGenerator = { value ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = range,
                name = name,
            )
        },
    )

    public constructor(
        min: Int,
        max: Int,
        name: String,
    ) : this(
        range = min..max,
        violationGenerator = { value ->
            StringViolationProvider.Default.lengthBetween(
                value = value,
                range = min..max,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            value.length in range,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringLengthBetweenRule(
    public val range: IntRange,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.lengthBetween(
            value = value,
            range = range,
            name = name,
        )
    },
) : NamedRule<String> {
    public constructor(
        min: Int,
        max: Int,
        violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
            StringViolationProvider.Default.lengthBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            value.value.length in range,
        ) {
            violationGenerator(value)
        }
    }
}
