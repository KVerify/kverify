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
    public operator fun plus(other: ValidationContext): ValidationContext {
        if (other === EmptyValidationContext) return this

        return other.fold(this) { acc, element ->
            if (acc === EmptyValidationContext) {
                element
            } else {
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
        override fun iterator(): Iterator<Element> = iterator { yield(this@Element) }
    }
}

/**
 * Extracts the [ValidationPath] accumulated in this context.
 *
 * Collects all [ValidationPathElement]s in iteration order, ignoring any non-path elements,
 * and wraps them in a [ValidationPath]. Returns a [ValidationPath] with an empty
 * [ValidationPath.elements] list if no path elements are present.
 */
public fun ValidationContext.validationPath(): ValidationPath = ValidationPath(filterIsInstance<ValidationPathElement>())

/**
 * Returns the last element of type [T] in this context, or `null` if no such element exists.
 *
 * Elements are examined in iteration order — the last matching element is returned,
 * meaning the most recently added element of type [T] takes precedence over earlier ones.
 * Non-matching elements are skipped.
 */
public inline fun <reified T : ValidationContext.Element> ValidationContext.lastOfTypeOrNull(): T? {
    var result: T? = null

    for (element in this) {
        if (element is T) result = element
    }

    return result
}
