package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.ComparableViolationProvider

public open class ComparableGreaterThanOrEqualToRule<T : Comparable<T>>(
    public val other: T,
    public val violationGenerator: ValueViolationGenerator<T> = { value ->
        ComparableViolationProvider.Default.greaterThanOrEqualTo(
            other = other,
            value = value,
        )
    },
) : Rule<T> {
    public constructor(
        other: T,
        name: String,
    ) : this(
        other = other,
        violationGenerator = { value ->
            ComparableViolationProvider.Default.greaterThanOrEqualTo(
                other = other,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: T) {
        validate(
            value >= other,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedComparableGreaterThanOrEqualToRule<T : Comparable<T>>(
    public val other: T,
    public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
        ComparableViolationProvider.Default.greaterThanOrEqualTo(
            other = other,
            value = value,
            name = name,
        )
    },
) : NamedRule<T> {
    override fun ValidationContext.runValidation(value: NamedValue<T>) {
        validate(
            value.value >= other,
        ) {
            violationGenerator(value)
        }
    }
}
