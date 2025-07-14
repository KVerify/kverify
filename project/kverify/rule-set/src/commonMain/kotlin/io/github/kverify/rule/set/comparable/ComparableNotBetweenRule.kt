package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.ComparableViolationFactory

public open class ComparableNotBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationGenerator: ValueViolationGenerator<T> = { value ->
        ComparableViolationFactory.Default.notBetween(
            range = range,
            value = value,
        )
    },
) : Rule<T> {
    public constructor(
        range: ClosedRange<T>,
        name: String,
    ) : this(
        range = range,
        violationGenerator = { value ->
            ComparableViolationFactory.Default.notBetween(
                range = range,
                value = value,
                name = name,
            )
        },
    )

    public constructor(
        min: T,
        max: T,
        violationGenerator: ValueViolationGenerator<T> = { value ->
            ComparableViolationFactory.Default.notBetween(
                range = min..max,
                value = value,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    public constructor(
        min: T,
        max: T,
        name: String,
    ) : this(
        range = min..max,
        violationGenerator = { value ->
            ComparableViolationFactory.Default.notBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: T) {
        validate(
            value !in range,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedComparableNotBetweenRule<T : Comparable<T>>(
    public val range: ClosedRange<T>,
    public val violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
        ComparableViolationFactory.Default.notBetween(
            range = range,
            value = value,
            name = name,
        )
    },
) : NamedRule<T> {
    public constructor(
        min: T,
        max: T,
        violationGenerator: NamedValueViolationGenerator<T> = { (name, value) ->
            ComparableViolationFactory.Default.notBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<T>) {
        validate(
            value.value !in range,
        ) {
            violationGenerator(value)
        }
    }
}
