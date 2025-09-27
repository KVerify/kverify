package io.github.kverify.rule.set.provider

import io.github.kverify.core.check.ViolationFactory
import io.github.kverify.core.rule.Rule

@Suppress("TooManyFunctions")
public interface CollectionRuleProvider {
    public fun <E, C : Collection<E>> containsAll(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> containsNone(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(elements: Collection<E>): Rule<C>

    public fun <E, C : Collection<E>> contains(element: E): Rule<C>

    public fun <C : Collection<*>> distinct(): Rule<C>

    public fun <C : Collection<*>> maxSize(maxSize: Int): Rule<C>

    public fun <C : Collection<*>> minSize(minSize: Int): Rule<C>

    public fun <E, C : Collection<E>> notContains(element: E): Rule<C>

    public fun <C : Collection<*>> notEmpty(): Rule<C>

    public fun <C : Collection<*>> notOfSize(size: Int): Rule<C>

    public fun <C : Collection<*>> ofSize(size: Int): Rule<C>

    public fun <C : Collection<*>> sizeBetween(sizeRange: IntRange): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(sizeRange: IntRange): Rule<C>
}

@Suppress("TooManyFunctions")
public interface CollectionRuleWithFactoryProvider {
    public fun <E, C : Collection<E>> containsAll(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> containsNone(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> containsOnly(
        elements: Collection<E>,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> contains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> distinct(violationFactory: ViolationFactory<C>): Rule<C>

    public fun <C : Collection<*>> maxSize(
        maxSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> minSize(
        minSize: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <E, C : Collection<E>> notContains(
        element: E,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> notEmpty(violationFactory: ViolationFactory<C>): Rule<C>

    public fun <C : Collection<*>> notOfSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> ofSize(
        size: Int,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> sizeBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>

    public fun <C : Collection<*>> sizeNotBetween(
        sizeRange: IntRange,
        violationFactory: ViolationFactory<C>,
    ): Rule<C>
}
