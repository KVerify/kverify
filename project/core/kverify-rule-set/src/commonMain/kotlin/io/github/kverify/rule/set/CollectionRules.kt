package io.github.kverify.rule.set

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validate
import io.github.kverify.core.model.NamedRule
import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.CollectionViolations

public open class CollectionRules(
    public val collectionViolations: CollectionViolations = CollectionViolations.Default,
) {
    public inner class OfSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.ofSize(size, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedOfSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class NotOfSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notOfSize(size, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedNotOfSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class MaxSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.maxSize(size, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedMaxSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class MinSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.minSize(size, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedMinSize<C : Collection<*>>(
        public val size: Int,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class SizeBetween<C : Collection<*>>(
        public val range: IntRange,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeBetween(range, value)
        },
    ) : Rule<C> {
        public constructor(
            min: Int,
            max: Int,
            violationGenerator: (C) -> Violation = { value ->
                collectionViolations.sizeBetween(min..max, value)
            },
        ) : this(
            range = min..max,
            violationGenerator = violationGenerator,
        )

        public constructor(
            range: IntRange,
            name: String,
        ) : this(
            range = range,
            violationGenerator = { value ->
                collectionViolations.sizeBetween(range, value, name)
            },
        )

        public constructor(
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

    public inner class NamedSizeBetween<C : Collection<*>>(
        public val range: IntRange,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class SizeNotBetween<C : Collection<*>>(
        public val range: IntRange,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.sizeNotBetween(range, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedSizeNotBetween<C : Collection<*>>(
        public val range: IntRange,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class ContainsAll<T, C : Collection<T>>(
        public val elements: C,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsAll(elements, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedContainsAll<T, C : Collection<T>>(
        public val elements: C,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class ContainsNone<T, C : Collection<T>>(
        public val elements: C,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsNone(elements, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedContainsNone<T, C : Collection<T>>(
        public val elements: C,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class Contains<T, C : Collection<T>>(
        public val element: T,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.contains(element, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedContains<T, C : Collection<T>>(
        public val element: T,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class NotContains<T, C : Collection<T>>(
        public val element: T,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.notContains(element, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedNotContains<T, C : Collection<T>>(
        public val element: T,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class ContainsOnly<T, C : Collection<T>>(
        public val elements: Collection<T>,
        public val violationGenerator: (C) -> Violation = { value ->
            collectionViolations.containsOnly(elements, value)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedContainsOnly<T, C : Collection<T>>(
        public val elements: Collection<T>,
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class NotEmpty<C : Collection<*>>(
        public val violationGenerator: (C) -> Violation = {
            collectionViolations.notEmpty(it)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedNotEmpty<C : Collection<*>>(
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public inner class Distinct<C : Collection<*>>(
        public val violationGenerator: (C) -> Violation = {
            collectionViolations.distinct(it)
        },
    ) : Rule<C> {
        public constructor(
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

    public inner class NamedDistinct<C : Collection<*>>(
        public val violationGenerator: (NamedValue<C>) -> Violation = { (name, value) ->
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

    public companion object : CollectionRules(
        collectionViolations = CollectionViolations.Default,
    )
}
