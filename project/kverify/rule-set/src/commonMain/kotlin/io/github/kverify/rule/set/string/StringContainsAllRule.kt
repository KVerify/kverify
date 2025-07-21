package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.StringViolationProvider

public open class StringContainsAllRule(
    public val chars: Iterable<Char>,
    public val violationGenerator: ValueViolationGenerator<String> = { value ->
        StringViolationProvider.Default.containsAll(
            chars = chars,
            value = value,
        )
    },
) : Rule<String> {
    public constructor(
        chars: Iterable<Char>,
        name: String,
    ) : this(
        chars = chars,
        violationGenerator = { value ->
            StringViolationProvider.Default.containsAll(
                chars = chars,
                value = value,
                name = name,
            )
        },
    )

    public constructor(
        string: String,
        violationGenerator: ValueViolationGenerator<String> = { value ->
            StringViolationProvider.Default.containsAll(
                chars = string.asIterable(),
                value = value,
            )
        },
    ) : this(
        chars = string.asIterable(),
        violationGenerator = violationGenerator,
    )

    public constructor(
        string: String,
        name: String,
    ) : this(
        chars = string.asIterable(),
        violationGenerator = { value ->
            StringViolationProvider.Default.containsAll(
                chars = string.asIterable(),
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: String) {
        validate(
            chars.all { it in value },
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedStringContainsAllRule(
    public val chars: Iterable<Char>,
    public val violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
        StringViolationProvider.Default.containsAll(
            chars = chars,
            value = value,
            name = name,
        )
    },
) : NamedRule<String> {
    public constructor(
        string: String,
        violationGenerator: NamedValueViolationGenerator<String> = { (name, value) ->
            StringViolationProvider.Default.containsAll(
                chars = string.asIterable(),
                value = value,
                name = name,
            )
        },
    ) : this(
        chars = string.asIterable(),
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<String>) {
        validate(
            chars.all { it in value.value },
        ) {
            violationGenerator(value)
        }
    }
}
