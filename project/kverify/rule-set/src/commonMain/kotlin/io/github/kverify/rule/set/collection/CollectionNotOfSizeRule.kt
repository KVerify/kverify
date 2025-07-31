package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public open class CollectionNotOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationProvider.Default.notOfSize(
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
            CollectionViolationProvider.Default.notOfSize(
                size = size,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size != size,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionNotOfSizeRule<C : Collection<*>>(
    public val size: Int,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationProvider.Default.notOfSize(
            size = size,
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size != size,
        ) {
            violationGenerator(value)
        }
    }
}
