package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.rule.Rule
import io.github.kverify.named.model.NamedValue
import io.github.kverify.named.rule.NamedRule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.provider.CollectionViolationProvider

public open class CollectionMinSizeRule<C : Collection<*>>(
    public val minSize: Int,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationProvider.Default.minSize(value, minSize)
    },
) : Rule<C> {
    public constructor(
        minSize: Int,
        name: String,
    ) : this(
        minSize = minSize,
        violationGenerator = { value ->
            CollectionViolationProvider.Default.minSize(
                minSize = minSize,
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.size >= minSize,
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionMinSizeRule<C : Collection<*>>(
    public val minSize: Int,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationProvider.Default.minSize(value, minSize, name)
    },
) : NamedRule<C> {
    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.size >= minSize,
        ) {
            violationGenerator(value)
        }
    }
}
