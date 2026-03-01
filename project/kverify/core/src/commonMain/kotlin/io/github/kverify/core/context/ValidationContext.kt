package io.github.kverify.core.context

public interface ValidationContext {
    public fun <R> fold(
        initial: R,
        operation: (R, Element) -> R,
    ): R

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

    public interface Element : ValidationContext {
        override fun <R> fold(
            initial: R,
            operation: (R, Element) -> R,
        ): R = operation(initial, this)
    }
}

public fun ValidationContext.validationPath(): List<ValidationPathElement> {
    val context = this

    return buildList {
        context.fold(this) { acc, element ->
            if (element is ValidationPathElement) acc.add(element)
            acc
        }
    }
}
