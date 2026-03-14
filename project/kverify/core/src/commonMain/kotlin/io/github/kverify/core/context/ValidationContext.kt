package io.github.kverify.core.context

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
 * Returns the list of [ValidationPathElement]s accumulated in this context.
 *
 * Non-path elements are ignored. Returns an empty list if no path elements are present.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationContext.validationPath(): List<ValidationPathElement> = filterIsInstance<ValidationPathElement>()
