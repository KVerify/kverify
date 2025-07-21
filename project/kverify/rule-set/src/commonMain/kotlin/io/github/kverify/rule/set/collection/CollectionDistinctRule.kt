package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public open class CollectionDistinctRule<C : Collection<*>>(
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationProvider.Default.distinct(
            value = value,
        )
    },
) : Rule<C> {
    public constructor(
        name: String,
    ) : this(
        violationGenerator = { value ->
            CollectionViolationProvider.Default.distinct(
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size == value.toSet().size,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionDistinctRule<C : Collection<*>>(
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationProvider.Default.distinct(
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size == value.value.toSet().size,
        ) {
            violationGenerator(value)
        }
    }
}
