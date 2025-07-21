package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public open class CollectionSizeBetweenRule<C : Collection<*>>(
    public val range: IntRange,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationProvider.Default.sizeBetween(
            range = range,
            value = value,
        )
    },
) : Rule<C> {
    public constructor(
        range: IntRange,
        name: String,
    ) : this(
        range = range,
        violationGenerator = { value ->
            CollectionViolationProvider.Default.sizeBetween(
                range = range,
                value = value,
                name = name,
            )
        },
    )

    public constructor(
        min: Int,
        max: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationProvider.Default.sizeBetween(
                range = min..max,
                value = value,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    public constructor(
        min: Int,
        max: Int,
        name: String,
    ) : this(
        range = min..max,
        violationGenerator = { value ->
            CollectionViolationProvider.Default.sizeBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size in range,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionSizeBetweenRule<C : Collection<*>>(
    public val range: IntRange,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationProvider.Default.sizeBetween(
            range = range,
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    public constructor(
        min: Int,
        max: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationProvider.Default.sizeBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ) : this(
        range = min..max,
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size in range,
        ) {
            violationGenerator(value)
        }
    }
}
