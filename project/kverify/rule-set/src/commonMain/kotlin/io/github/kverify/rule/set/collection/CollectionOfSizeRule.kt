package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.CollectionViolationFactory

public open class CollectionOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationFactory.Default.ofSize(
            size = size,
            value = value,
        )
    },
) : Rule<C> {
    public constructor(
        size: Int,
        name: String,
    ) : this(
        size = size,
        violationGenerator = { value ->
            CollectionViolationFactory.Default.minSize(
                size = size,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size == size,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationFactory.Default.ofSize(
            size = size,
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size == size,
        ) {
            violationGenerator(value)
        }
    }
}
