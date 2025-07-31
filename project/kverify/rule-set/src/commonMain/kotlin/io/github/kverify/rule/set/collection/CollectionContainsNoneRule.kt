package io.github.kverify.rule.set.collection

import io.github.kverify.check.set.collection.CollectionContainsNoneCheck
import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.violation.factory.provider.CollectionViolationFactoryProvider

public class CollectionContainsNoneRule<E, C : Collection<E>>(
    public val elements: Collection<E>,
    public val violationFactory: ViolationFactory<C> =
        CollectionViolationFactoryProvider.Default.containsNone(
            elements = elements,
        ),
) : Rule<C> by PredicateRule(
        validationCheck = CollectionContainsNoneCheck(elements),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(
    vararg elements: E,
    violationFactory: ViolationFactory<C>,
): CollectionContainsNoneRule<E, C> {
    val elementsSet = setOf(elements = elements)

    return CollectionContainsNoneRule(
        elements = elementsSet,
        violationFactory = violationFactory,
    )
}

@Suppress("NOTHING_TO_INLINE")
public inline fun <E, C : Collection<E>> CollectionContainsNoneRule(vararg elements: E): CollectionContainsNoneRule<E, C> {
    val elementsSet = setOf(elements = elements)

    return CollectionContainsNoneRule(elements = elementsSet)
}

private fun disjoint(
    c1: Collection<*>,
    c2: Collection<*>,
): Boolean {
    if (c1.isEmpty() || c2.isEmpty()) return true

    val iterate: Collection<*>
    val contains: Collection<*>

    when {
        c1 is Set<*> -> {
            iterate = c2
            contains = c1
        }

        c2 is Set<*> -> {
            iterate = c1
            contains = c2
        }

        c1.size > c2.size -> {
            iterate = c2
            contains = c1
        }

        else -> {
            iterate = c1
            contains = c2
        }
    }

    return iterate.none { it in contains }
}
