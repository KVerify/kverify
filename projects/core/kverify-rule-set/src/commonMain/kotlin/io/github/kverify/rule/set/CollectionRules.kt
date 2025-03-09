package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.CollectionViolations

open class CollectionRules(
    val collectionViolations: CollectionViolations = CollectionViolations.Default,
) {
    inner class OfSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.ofSize(size, value)
        },
    ) : Rule<C> {
        constructor(
            size: Int,
            name: String,
        ) : this(
            size = size,
            violationGenerator = { value ->
                collectionViolations.ofSize(size, value, name)
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

    inner class NamedOfSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.ofSize(size, value, name)
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

    inner class NotOfSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notOfSize(size, value)
        },
    ) : Rule<C> {
        constructor(
            size: Int,
            name: String,
        ) : this(
            size = size,
            violationGenerator = { value ->
                collectionViolations.notOfSize(size, value, name)
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

    inner class NamedNotOfSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notOfSize(size, value, name)
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

    inner class MaxSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.maxSize(size, value)
        },
    ) : Rule<C> {
        constructor(
            size: Int,
            name: String,
        ) : this(
            size = size,
            violationGenerator = { value ->
                collectionViolations.maxSize(size, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.size <= size,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedMaxSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.maxSize(size, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.size <= size,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class MinSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.minSize(size, value)
        },
    ) : Rule<C> {
        constructor(
            size: Int,
            name: String,
        ) : this(
            size = size,
            violationGenerator = { value ->
                collectionViolations.minSize(size, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.size >= size,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedMinSize<C : Collection<*>>(
        val size: Int,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.minSize(size, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.size >= size,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class SizeBetween<C : Collection<*>>(
        val range: IntRange,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeBetween(range, value)
        },
    ) : Rule<C> {
        constructor(
            min: Int,
            max: Int,
            violationGenerator: (C) -> Violation = { value ->
                collectionViolations.sizeBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                collectionViolations.sizeBetween(range, value, name)
            },
        )

        constructor(
            min: Int,
            max: Int,
            name: String,
        ) : this(
            range = min..max,
            violationGenerator = { value ->
                collectionViolations.sizeBetween(min..max, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.size in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedSizeBetween<C : Collection<*>>(
        val range: IntRange,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeBetween(range, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.size in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class SizeNotBetween<C : Collection<*>>(
        val range: IntRange,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeNotBetween(range, value)
        },
    ) : Rule<C> {
        constructor(
            min: Int,
            max: Int,
            violationGenerator: (C) -> Violation = { value ->
                collectionViolations.sizeNotBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.size !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedSizeNotBetween<C : Collection<*>>(
        val range: IntRange,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.sizeNotBetween(range, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.size !in range,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsAll<T, C : Collection<T>>(
        val elements: C,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsAll(elements, value)
        },
    ) : Rule<C> {
        constructor(
            elements: C,
            name: String,
        ) : this(
            elements = elements,
            violationGenerator = { value ->
                collectionViolations.containsAll(elements, value, name)
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

    inner class NamedContainsAll<T, C : Collection<T>>(
        val elements: C,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsAll(elements, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.containsAll(elements),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsNone<T, C : Collection<T>>(
        val elements: C,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsNone(elements, value)
        },
    ) : Rule<C> {
        constructor(
            elements: C,
            name: String,
        ) : this(
            elements = elements,
            violationGenerator = { value ->
                collectionViolations.containsNone(elements, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                elements.none { it in value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsNone<T, C : Collection<T>>(
        val elements: C,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsNone(elements, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                elements.none { it in value.value },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Contains<T, C : Collection<T>>(
        val element: T,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.contains(element, value)
        },
    ) : Rule<C> {
        constructor(
            element: T,
            name: String,
        ) : this(
            element = element,
            violationGenerator = { value ->
                collectionViolations.contains(element, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.contains(element),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContains<T, C : Collection<T>>(
        val element: T,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.contains(element, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.contains(element),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotContains<T, C : Collection<T>>(
        val element: T,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notContains(element, value)
        },
    ) : Rule<C> {
        constructor(
            element: T,
            name: String,
        ) : this(
            element = element,
            violationGenerator = { value ->
                collectionViolations.notContains(element, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                !value.contains(element),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotContains<T, C : Collection<T>>(
        val element: T,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notContains(element, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                !value.value.contains(element),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class ContainsOnly<T, C : Collection<T>>(
        val elements: Collection<T>,
        val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsOnly(elements, value)
        },
    ) : Rule<C> {
        constructor(
            elements: Collection<T>,
            name: String,
        ) : this(
            elements = elements,
            violationGenerator = { value ->
                collectionViolations.containsOnly(elements, value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.all { element -> element in elements },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedContainsOnly<T, C : Collection<T>>(
        val elements: Collection<T>,
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.containsOnly(elements, value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.all { element -> element in elements },
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NotEmpty<C : Collection<*>>(
        val violationGenerator: (C) -> Violation = {
            collectionViolations.notEmpty(it)
        },
    ) : Rule<C> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                collectionViolations.notEmpty(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedNotEmpty<C : Collection<*>>(
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.notEmpty(value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.isNotEmpty(),
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class Distinct<C : Collection<*>>(
        val violationGenerator: (C) -> Violation = {
            collectionViolations.distinct(it)
        },
    ) : Rule<C> {
        constructor(
            name: String,
        ) : this(
            violationGenerator = { value ->
                collectionViolations.distinct(value, name)
            },
        )

        override fun ValidationContext.runValidation(value: C) {
            validate(
                value.size == value.toSet().size,
            ) {
                violationGenerator(value)
            }
        }
    }

    inner class NamedDistinct<C : Collection<*>>(
        val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
            collectionViolations.distinct(value, name)
        },
    ) : NamedRule<C> {
        override fun ValidationContext.runValidation(value: NamedValue<C>) {
            validate(
                value.value.size == value.value.toSet().size,
            ) {
                violationGenerator(value)
            }
        }
    }

    companion object : CollectionRules(
        collectionViolations = CollectionViolations.Default,
    )
}
