package io.github.kverify.named.rule.set.provider

import io.github.kverify.named.check.NamedViolationFactory
import io.github.kverify.named.rule.NamedRule

@Suppress("TooManyFunctions")
public interface NamedCollectionRuleProvider {
    public fun <E, C : Collection<E>> namedContainsAll(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(elements: Collection<E>): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(element: E): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(maxSize: Int): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(minSize: Int): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(element: E): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(size: Int): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(size: Int): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(sizeRange: IntRange): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(sizeRange: IntRange): NamedRule<C>
}

@Suppress("TooManyFunctions")
public interface NamedCollectionRuleWithFactoryProvider {
    public fun <E, C : Collection<E>> namedContainsAll(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsNone(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContainsOnly(
        elements: Collection<E>,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedDistinct(violationFactory: NamedViolationFactory<C>): NamedRule<C>

    public fun <C : Collection<*>> namedMaxSize(
        maxSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedMinSize(
        minSize: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <E, C : Collection<E>> namedNotContains(
        element: E,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedNotEmpty(violationFactory: NamedViolationFactory<C>): NamedRule<C>

    public fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedOfSize(
        size: Int,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>

    public fun <C : Collection<*>> namedSizeNotBetween(
        sizeRange: IntRange,
        violationFactory: NamedViolationFactory<C>,
    ): NamedRule<C>
}
