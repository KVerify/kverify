package io.github.kverify.core.context

import io.github.kverify.core.model.ValidationPath

/**
 * An immutable, composable container of contextual information threaded through the validation process.
 *
 * A [ValidationContext] is an [Iterable] sequence of [Element]s. Each element is an
 * indivisible unit of context — for example, a [ValidationPathElement] that contributes one
 * segment to the validation path.
 */
public interface ValidationContext : Iterable<ValidationContext.Element> {
    /**
     * Returns a new context containing elements from both contexts.
     */
    public operator fun plus(other: ValidationContext): ValidationContext =
        when {
            this === EmptyValidationContext -> {
                other
            }

            other is Element -> {
                CombinedContext(this, other)
            }

            other === EmptyValidationContext -> {
                this
            }

            else -> {
                other.fold(this) { acc, element ->
                    CombinedContext(acc, element)
                }
            }
        }

    /**
     * A single, indivisible unit of a [ValidationContext].
     *
     * An [Element] is itself a valid [ValidationContext] containing exactly itself, so it can be
     * passed anywhere a [ValidationContext] is expected.
     */
    public interface Element : ValidationContext {
        override fun iterator(): Iterator<Element> = SingleElementIterator(this)
    }
}

/**
 * Extracts the [ValidationPath] accumulated in this context.
 *
 * Collects all [ValidationPathElement]s in iteration order, ignoring any non-path elements,
 * and wraps them in a [ValidationPath]. Returns a [ValidationPath] with an empty
 * [ValidationPath.elements] list if no path elements are present.
 */
public fun ValidationContext.validationPath(): ValidationPath =
    when {
        this is CombinedContext -> ValidationPath(pathElements())
        this is ValidationPathElement -> ValidationPath(listOf(this))
        this === EmptyValidationContext -> ValidationPath.Empty
        else -> ValidationPath(filterIsInstance<ValidationPathElement>())
    }

private class SingleElementIterator(
    private val element: ValidationContext.Element,
) : Iterator<ValidationContext.Element> {
    private var hasNext: Boolean = true

    override fun next(): ValidationContext.Element {
        if (!hasNext) throw NoSuchElementException()
        hasNext = false
        return element
    }

    override fun hasNext(): Boolean = hasNext
}
