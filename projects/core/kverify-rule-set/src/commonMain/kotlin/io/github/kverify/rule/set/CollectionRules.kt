package io.github.kverify.rule.set

import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.CollectionViolations

@Suppress("TooManyFunctions")
open class CollectionRules(
    val collectionViolations: CollectionViolations = CollectionViolations.Default,
) {
    // Simple value rules with generator
    inline fun <C : Collection<*>> ofSize(
        size: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.ofSize(size, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size == size,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> notOfSize(
        size: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notOfSize(size, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size != size,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> maxSize(
        size: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.maxSize(size, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size <= size,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> minSize(
        size: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.minSize(size, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size >= size,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> sizeBetween(
        range: IntRange,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeBetween(range, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> sizeNotBetween(
        range: IntRange,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeNotBetween(range, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size !in range,
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeBetween(min..max, value)
        },
    ): Rule<C> =
        sizeBetween(
            min..max,
            violationGenerator,
        )

    inline fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeNotBetween(min..max, value)
        },
    ): Rule<C> =
        sizeNotBetween(
            min..max,
            violationGenerator,
        )

    inline fun <T, C : Collection<T>> containsAll(
        elements: C,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsAll(elements, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.containsAll(elements),
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T, C : Collection<T>> containsNone(
        elements: C,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsNone(elements, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                elements.none { it in value },
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T, C : Collection<T>> contains(
        element: T,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.contains(element, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.contains(element),
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T, C : Collection<T>> notContains(
        element: T,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notContains(element, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                !value.contains(element),
            ) {
                violationGenerator(value)
            }
        }

    inline fun <T, C : Collection<T>> containsOnly(
        elements: Collection<T>,
        crossinline violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsOnly(elements, value)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.all { element -> element in elements },
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> notEmpty(
        crossinline violationGenerator: (C) -> Violation = {
            collectionViolations.notEmpty(it)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }

    inline fun <C : Collection<*>> distinct(
        crossinline violationGenerator: (C) -> Violation = {
            collectionViolations.distinct(it)
        },
    ): Rule<C> =
        Rule { value ->
            validate(
                value.size == value.toSet().size,
            ) {
                violationGenerator(value)
            }
        }

    // Simple value rules with name
    fun <C : Collection<*>> ofSize(
        size: Int,
        name: String,
    ): Rule<C> =
        ofSize(size) { value ->
            collectionViolations.ofSize(size, value, name)
        }

    fun <C : Collection<*>> notOfSize(
        size: Int,
        name: String,
    ): Rule<C> =
        notOfSize(size) { value ->
            collectionViolations.notOfSize(size, value, name)
        }

    fun <C : Collection<*>> maxSize(
        size: Int,
        name: String,
    ): Rule<C> =
        maxSize(size) { value ->
            collectionViolations.maxSize(size, value, name)
        }

    fun <C : Collection<*>> minSize(
        size: Int,
        name: String,
    ): Rule<C> =
        minSize(size) { value ->
            collectionViolations.minSize(size, value, name)
        }

    fun <C : Collection<*>> sizeBetween(
        range: IntRange,
        name: String,
    ): Rule<C> =
        sizeBetween(range) { value ->
            collectionViolations.sizeBetween(range, value, name)
        }

    fun <C : Collection<*>> sizeBetween(
        min: Int,
        max: Int,
        name: String,
    ): Rule<C> =
        sizeBetween(min, max) { value ->
            collectionViolations.sizeBetween(min..max, value, name)
        }

    fun <C : Collection<*>> sizeNotBetween(
        range: IntRange,
        name: String,
    ): Rule<C> =
        sizeNotBetween(range) { value ->
            collectionViolations.sizeNotBetween(range, value, name)
        }

    fun <C : Collection<*>> sizeNotBetween(
        min: Int,
        max: Int,
        name: String,
    ): Rule<C> =
        sizeNotBetween(min, max) { value ->
            collectionViolations.sizeNotBetween(min..max, value, name)
        }

    fun <T, C : Collection<T>> containsAll(
        elements: C,
        name: String,
    ): Rule<C> =
        containsAll(elements) { value ->
            collectionViolations.containsAll(elements, value, name)
        }

    fun <T, C : Collection<T>> containsNone(
        elements: C,
        name: String,
    ): Rule<C> =
        containsNone(elements) { value ->
            collectionViolations.containsNone(elements, value, name)
        }

    fun <T, C : Collection<T>> contains(
        element: T,
        name: String,
    ): Rule<C> =
        contains(element) { value ->
            collectionViolations.contains(element, value, name)
        }

    fun <T, C : Collection<T>> notContains(
        element: T,
        name: String,
    ): Rule<C> =
        notContains(element) { value ->
            collectionViolations.notContains(element, value, name)
        }

    fun <T, C : Collection<T>> containsOnly(
        elements: Collection<T>,
        name: String,
    ): Rule<C> =
        containsOnly(elements) { value ->
            collectionViolations.containsOnly(elements, value, name)
        }

    fun <C : Collection<*>> notEmpty(name: String): Rule<C> =
        notEmpty { value ->
            collectionViolations.notEmpty(value, name)
        }

    fun <C : Collection<*>> distinct(name: String): Rule<C> =
        distinct { value ->
            collectionViolations.distinct(value, name)
        }

    // Named value rules
    inline fun <C : Collection<*>> namedOfSize(
        size: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.ofSize(size, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                ofSize<C>(size) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedNotOfSize(
        size: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notOfSize(size, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                notOfSize<C>(size) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedMaxSize(
        size: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.maxSize(size, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                maxSize<C>(size) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedMinSize(
        size: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.minSize(size, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                minSize<C>(size) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedSizeBetween(
        range: IntRange,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeBetween(range, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                sizeBetween<C>(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedSizeBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeBetween(min..max, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                sizeBetween<C>(min, max) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedSizeNotBetween(
        range: IntRange,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeNotBetween(range, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                sizeNotBetween<C>(range) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedSizeNotBetween(
        min: Int,
        max: Int,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeNotBetween(min..max, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                sizeNotBetween<C>(min, max) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T, C : Collection<T>> namedContainsAll(
        elements: C,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsAll(elements, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                containsAll(elements) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T, C : Collection<T>> namedContainsNone(
        elements: C,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsNone(elements, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                containsNone(elements) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T, C : Collection<T>> namedContains(
        element: T,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.contains(element, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                contains(element) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T, C : Collection<T>> namedNotContains(
        element: T,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notContains(element, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                notContains(element) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <T, C : Collection<T>> namedContainsOnly(
        elements: Collection<T>,
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsOnly(elements, value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                containsOnly(elements) {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedNotEmpty(
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notEmpty(value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                notEmpty<C> {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    inline fun <C : Collection<*>> namedDistinct(
        crossinline violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.distinct(value, name)
        },
    ): NamedRule<C> =
        NamedRule { namedValue ->
            val rule =
                distinct<C> {
                    violationGenerator(namedValue)
                }

            namedValue.value.applyRules(rule)
        }

    companion object : CollectionRules(
        collectionViolations = CollectionViolations.Default,
    )
}
