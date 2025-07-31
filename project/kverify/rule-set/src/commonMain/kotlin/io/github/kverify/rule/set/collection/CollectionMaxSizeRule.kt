package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public open class CollectionMaxSizeRule<C : Collection<*>>(
    public val maxSize: Int,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationProvider.Default.maxSize(
            maxSize = maxSize,
            value = value,
        )
    },
) : Rule<C> {
    public constructor(
        maxSize: Int,
        name: String,
    ) : this(
        maxSize = maxSize,
        violationGenerator = { value ->
            CollectionViolationProvider.Default.maxSize(
                maxSize = maxSize,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size <= maxSize,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionMaxSizeRule<C : Collection<*>>(
    public val maxSize: Int,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationProvider.Default.maxSize(
            maxSize = maxSize,
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size <= maxSize,
        ) {
            violationGenerator(value)
        }
    }
}
