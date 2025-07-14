package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.rule.NamedRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.violation.set.factory.CollectionViolationFactory

public open class CollectionContainsAllRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationGenerator: ValueViolationGenerator<C> = { value ->
        CollectionViolationFactory.Default.containsAll(
            elements = elements,
            value = value,
        )
    },
) : Rule<C> {
    public constructor(
        elements: Collection<E>,
        name: String,
    ) : this(
        elements = elements,
        violationGenerator = { value ->
            CollectionViolationFactory.Default.containsAll(
                elements = elements,
                value = value,
                name = name,
            )
        },
    )

    public constructor(
        vararg elements: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.containsAll(
                elements = elements.asList(),
                value = value,
            )
        },
    ) : this(
        elements = elements.asList(),
        violationGenerator = violationGenerator,
    )

    public constructor(
        vararg elements: E,
        name: String,
    ) : this(
        elements = elements.asList(),
        violationGenerator = { value ->
            CollectionViolationFactory.Default.containsAll(
                elements = elements.asList(),
                value = value,
                name = name,
            )
        },
    )

    override fun ValidationContext.runValidation(value: C) {
        validate(
            value.containsAll(elements),
        ) {
            violationGenerator(value)
        }
    }
}

public open class NamedCollectionContainsAllRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
        CollectionViolationFactory.Default.containsAll(
            elements = elements,
            value = value,
            name = name,
        )
    },
) : NamedRule<C> {
    public constructor(
        vararg elements: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.containsAll(
                elements = elements.asList(),
                value = value,
                name = name,
            )
        },
    ) : this(
        elements = elements.asList(),
        violationGenerator = violationGenerator,
    )

    override fun ValidationContext.runValidation(value: NamedValue<C>) {
        validate(
            value.value.containsAll(elements),
        ) {
            violationGenerator(value)
        }
    }
}
