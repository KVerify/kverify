package io.github.kverify.core.context

public interface ValidationContext : Iterable<ValidationContext.Element> {
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
        override fun iterator(): Iterator<Element> = iterator { yield(this@Element) }
    }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun ValidationContext.validationPath(): List<ValidationPathElement> = filterIsInstance<ValidationPathElement>()
