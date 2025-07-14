package io.github.kverify.rule.set.factory

import io.github.kverify.rule.set.NamedValueViolationGenerator
import io.github.kverify.rule.set.ValueViolationGenerator
import io.github.kverify.rule.set.collection.CollectionContainsAllRule
import io.github.kverify.rule.set.collection.CollectionContainsNoneRule
import io.github.kverify.rule.set.collection.CollectionContainsOnlyRule
import io.github.kverify.rule.set.collection.CollectionContainsRule
import io.github.kverify.rule.set.collection.CollectionDistinctRule
import io.github.kverify.rule.set.collection.CollectionMaxSizeRule
import io.github.kverify.rule.set.collection.CollectionMinSizeRule
import io.github.kverify.rule.set.collection.CollectionNotContainsRule
import io.github.kverify.rule.set.collection.CollectionNotEmptyRule
import io.github.kverify.rule.set.collection.CollectionNotOfSizeRule
import io.github.kverify.rule.set.collection.CollectionOfSizeRule
import io.github.kverify.rule.set.collection.CollectionSizeBetweenRule
import io.github.kverify.rule.set.collection.CollectionSizeNotBetweenRule
import io.github.kverify.rule.set.collection.NamedCollectionContainsAllRule
import io.github.kverify.rule.set.collection.NamedCollectionContainsNoneRule
import io.github.kverify.rule.set.collection.NamedCollectionContainsOnlyRule
import io.github.kverify.rule.set.collection.NamedCollectionContainsRule
import io.github.kverify.rule.set.collection.NamedCollectionDistinctRule
import io.github.kverify.rule.set.collection.NamedCollectionMaxSizeRule
import io.github.kverify.rule.set.collection.NamedCollectionMinSizeRule
import io.github.kverify.rule.set.collection.NamedCollectionNotContainsRule
import io.github.kverify.rule.set.collection.NamedCollectionNotEmptyRule
import io.github.kverify.rule.set.collection.NamedCollectionNotOfSizeRule
import io.github.kverify.rule.set.collection.NamedCollectionOfSizeRule
import io.github.kverify.rule.set.collection.NamedCollectionSizeBetweenRule
import io.github.kverify.rule.set.collection.NamedCollectionSizeNotBetweenRule
import io.github.kverify.violation.set.factory.CollectionViolationFactory

public interface CollectionRuleFactory {
    public val collectionViolationFactory: CollectionViolationFactory
        get() = CollectionViolationFactory.Default

    // region containsAll
    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsAll(
                elements = elements,
                value = value,
            )
        },
    ): CollectionContainsAllRule<E, C> =
        CollectionContainsAllRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        name: String,
    ): CollectionContainsAllRule<E, C> =
        CollectionContainsAllRule(
            elements = elements,
            name = name,
        )

    public fun <E, C : Collection<E>> containsAll(
        vararg elements: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsAll(
                elements = elements.asList(),
                value = value,
            )
        },
    ): CollectionContainsAllRule<E, C> =
        CollectionContainsAllRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsAll(
        vararg elements: E,
        name: String,
    ): CollectionContainsAllRule<E, C> =
        CollectionContainsAllRule(
            elements = elements.asList(),
            name = name,
        )
    // endregion

    // region namedContainsAll
    public fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsAll(
                elements = elements,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsAllRule<E, C> =
        NamedCollectionContainsAllRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> namedContainsAll(
        vararg elements: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsAll(
                elements = elements.asList(),
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsAllRule<E, C> =
        NamedCollectionContainsAllRule(
            elements = elements.asList(),
            violationGenerator = violationGenerator,
        )
    // endregion

    // region containsNone
    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsNone(
                elements = elements,
                value = value,
            )
        },
    ): CollectionContainsNoneRule<E, C> =
        CollectionContainsNoneRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        name: String,
    ): CollectionContainsNoneRule<E, C> =
        CollectionContainsNoneRule(
            elements = elements,
            name = name,
        )

    public fun <E, C : Collection<E>> containsNone(
        vararg elements: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsNone(
                elements = elements.asList(),
                value = value,
            )
        },
    ): CollectionContainsNoneRule<E, C> =
        CollectionContainsNoneRule(
            elements = elements.asList(),
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsNone(
        vararg elements: E,
        name: String,
    ): CollectionContainsNoneRule<E, C> =
        CollectionContainsNoneRule(
            elements = elements.asList(),
            name = name,
        )
    // endregion

    // region namedContainsNone
    public fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsNone(
                elements = elements,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsNoneRule<E, C> =
        NamedCollectionContainsNoneRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> namedContainsNone(
        vararg elements: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsNone(
                elements = elements.asList(),
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsNoneRule<E, C> =
        NamedCollectionContainsNoneRule(
            elements = elements.asList(),
            violationGenerator = violationGenerator,
        )
    // endregion

    // region containsOnly
    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsOnly(
                elements = elements,
                value = value,
            )
        },
    ): CollectionContainsOnlyRule<E, C> =
        CollectionContainsOnlyRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        name: String,
    ): CollectionContainsOnlyRule<E, C> =
        CollectionContainsOnlyRule(
            elements = elements,
            name = name,
        )

    public fun <E, C : Collection<E>> containsOnly(
        vararg elements: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.containsOnly(
                elements = elements.asList(),
                value = value,
            )
        },
    ): CollectionContainsOnlyRule<E, C> =
        CollectionContainsOnlyRule(
            elements = elements.asList(),
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> containsOnly(
        vararg elements: E,
        name: String,
    ): CollectionContainsOnlyRule<E, C> =
        CollectionContainsOnlyRule(
            elements = elements.asList(),
            name = name,
        )
    // endregion

    // region namedContainsOnly
    public fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsOnly(
                elements = elements,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsOnlyRule<E, C> =
        NamedCollectionContainsOnlyRule(
            elements = elements,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> namedContainsOnly(
        vararg elements: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.containsOnly(
                elements = elements.asList(),
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsOnlyRule<E, C> =
        NamedCollectionContainsOnlyRule(
            elements = elements.asList(),
            violationGenerator = violationGenerator,
        )
    // endregion

    // region contains
    public fun <E, C : Collection<E>> contains(
        element: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.contains(
                element = element,
                value = value,
            )
        },
    ): CollectionContainsRule<E, C> =
        CollectionContainsRule(
            element = element,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> contains(
        element: E,
        name: String,
    ): CollectionContainsRule<E, C> =
        CollectionContainsRule(
            element = element,
            name = name,
        )
    // endregion

    // region namedContains
    public fun <E, C : Collection<E>> namedContains(
        element: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.contains(
                element = element,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionContainsRule<E, C> =
        NamedCollectionContainsRule(
            element = element,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region distinct
    public fun <C : Collection<*>> distinct(
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.distinct(
                value = value,
            )
        },
    ): CollectionDistinctRule<C> =
        CollectionDistinctRule(
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> distinct(name: String): CollectionDistinctRule<C> =
        CollectionDistinctRule(
            name = name,
        )
    // endregion

    // region namedDistinct
    public fun <C : Collection<*>> namedDistinct(
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.distinct(
                value = value,
                name = name,
            )
        },
    ): NamedCollectionDistinctRule<C> =
        NamedCollectionDistinctRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region maxSize
    public fun <C : Collection<*>> maxSize(
        size: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.maxSize(
                value = value,
                size = size,
            )
        },
    ): CollectionMaxSizeRule<C> =
        CollectionMaxSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> maxSize(
        size: Int,
        name: String,
    ): CollectionMaxSizeRule<C> =
        CollectionMaxSizeRule(
            size = size,
            name = name,
        )
    // endregion

    // region namedMaxSize
    public fun <C : Collection<*>> namedMaxSize(
        size: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.maxSize(
                value = value,
                size = size,
                name = name,
            )
        },
    ): NamedCollectionMaxSizeRule<C> =
        NamedCollectionMaxSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region minSize
    public fun <C : Collection<*>> minSize(
        size: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.minSize(
                value = value,
                size = size,
            )
        },
    ): CollectionMinSizeRule<C> =
        CollectionMinSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> minSize(
        size: Int,
        name: String,
    ): CollectionMinSizeRule<C> =
        CollectionMinSizeRule(
            size = size,
            name = name,
        )
    // endregion

    // region namedMinSize
    public fun <C : Collection<*>> namedMinSize(
        size: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.minSize(
                value = value,
                size = size,
                name = name,
            )
        },
    ): NamedCollectionMinSizeRule<C> =
        NamedCollectionMinSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notContains
    public fun <E, C : Collection<E>> notContains(
        element: E,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            collectionViolationFactory.notContains(
                element = element,
                value = value,
            )
        },
    ): CollectionNotContainsRule<E, C> =
        CollectionNotContainsRule(
            element = element,
            violationGenerator = violationGenerator,
        )

    public fun <E, C : Collection<E>> notContains(
        element: E,
        name: String,
    ): CollectionNotContainsRule<E, C> =
        CollectionNotContainsRule(
            element = element,
            name = name,
        )
    // endregion

    // region namedNotContains
    public fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            collectionViolationFactory.notContains(
                element = element,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionNotContainsRule<E, C> =
        NamedCollectionNotContainsRule(
            element = element,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notEmpty
    public fun <C : Collection<*>> notEmpty(
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.notEmpty(
                value = value,
            )
        },
    ): CollectionNotEmptyRule<C> =
        CollectionNotEmptyRule(
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> notEmpty(name: String): CollectionNotEmptyRule<C> =
        CollectionNotEmptyRule(
            name = name,
        )
    // endregion

    // region namedNotEmpty
    public fun <C : Collection<*>> namedNotEmpty(
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.notEmpty(
                value = value,
                name = name,
            )
        },
    ): NamedCollectionNotEmptyRule<C> =
        NamedCollectionNotEmptyRule(
            violationGenerator = violationGenerator,
        )
    // endregion

    // region notOfSize
    public fun <C : Collection<*>> notOfSize(
        size: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.notOfSize(
                value = value,
                size = size,
            )
        },
    ): CollectionNotOfSizeRule<C> =
        CollectionNotOfSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> notOfSize(
        size: Int,
        name: String,
    ): CollectionNotOfSizeRule<C> =
        CollectionNotOfSizeRule(
            size = size,
            name = name,
        )
    // endregion

    // region namedNotOfSize
    public fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.notOfSize(
                value = value,
                size = size,
                name = name,
            )
        },
    ): NamedCollectionNotOfSizeRule<C> =
        NamedCollectionNotOfSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region ofSize
    public fun <C : Collection<*>> ofSize(
        size: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.ofSize(
                value = value,
                size = size,
            )
        },
    ): CollectionOfSizeRule<C> =
        CollectionOfSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> ofSize(
        size: Int,
        name: String,
    ): CollectionOfSizeRule<C> =
        CollectionOfSizeRule(
            size = size,
            name = name,
        )
    // endregion

    // region namedOfSize
    public fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.ofSize(
                value = value,
                size = size,
                name = name,
            )
        },
    ): NamedCollectionOfSizeRule<C> =
        NamedCollectionOfSizeRule(
            size = size,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region sizeBetween
    public fun <C : Collection<*>> sizeBetween(
        range: IntRange,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.sizeBetween(
                range = range,
                value = value,
            )
        },
    ): CollectionSizeBetweenRule<C> =
        CollectionSizeBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> sizeBetween(
        range: IntRange,
        name: String,
    ): CollectionSizeBetweenRule<C> =
        CollectionSizeBetweenRule(
            range = range,
            name = name,
        )

    public fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.sizeBetween(
                range = min..max,
                value = value,
            )
        },
    ): CollectionSizeBetweenRule<C> =
        CollectionSizeBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
        name: String,
    ): CollectionSizeBetweenRule<C> =
        CollectionSizeBetweenRule(
            min = min,
            max = max,
            name = name,
        )
    // endregion

    // region namedSizeBetween
    public fun <C : Collection<*>> namedSizeBetween(
        range: IntRange,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.sizeBetween(
                range = range,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionSizeBetweenRule<C> =
        NamedCollectionSizeBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> namedSizeBetween(
        min: Int,
        max: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.sizeBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionSizeBetweenRule<C> =
        NamedCollectionSizeBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion

    // region sizeNotBetween
    public fun <C : Collection<*>> sizeNotBetween(
        range: IntRange,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.sizeNotBetween(
                range = range,
                value = value,
            )
        },
    ): CollectionSizeNotBetweenRule<C> =
        CollectionSizeNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> sizeNotBetween(
        range: IntRange,
        name: String,
    ): CollectionSizeNotBetweenRule<C> =
        CollectionSizeNotBetweenRule(
            range = range,
            name = name,
        )

    public fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
        violationGenerator: ValueViolationGenerator<C> = { value ->
            CollectionViolationFactory.Default.sizeNotBetween(
                range = min..max,
                value = value,
            )
        },
    ): CollectionSizeNotBetweenRule<C> =
        CollectionSizeNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
        name: String,
    ): CollectionSizeNotBetweenRule<C> =
        CollectionSizeNotBetweenRule(
            min = min,
            max = max,
            name = name,
        )
    // endregion

    // region namedSizeNotBetween
    public fun <C : Collection<*>> namedSizeNotBetween(
        range: IntRange,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.sizeNotBetween(
                range = range,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionSizeNotBetweenRule<C> =
        NamedCollectionSizeNotBetweenRule(
            range = range,
            violationGenerator = violationGenerator,
        )

    public fun <C : Collection<*>> namedSizeNotBetween(
        min: Int,
        max: Int,
        violationGenerator: NamedValueViolationGenerator<C> = { (name, value) ->
            CollectionViolationFactory.Default.sizeNotBetween(
                range = min..max,
                value = value,
                name = name,
            )
        },
    ): NamedCollectionSizeNotBetweenRule<C> =
        NamedCollectionSizeNotBetweenRule(
            min = min,
            max = max,
            violationGenerator = violationGenerator,
        )
    // endregion
}

public open class CollectionRules(
    public override val collectionViolationFactory: CollectionViolationFactory,
) : CollectionRuleFactory {
    public companion object : CollectionRules(
        collectionViolationFactory = CollectionViolationFactory.Default,
    )
}
