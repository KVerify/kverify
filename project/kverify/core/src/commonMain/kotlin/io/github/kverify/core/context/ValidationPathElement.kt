package io.github.kverify.core.context

public class ValidationPathElement(
    override val key: ValidationPathKey,
) : ValidationContext.Element {
    public companion object {
        public fun property(name: String): ValidationPathElement =
            ValidationPathElement(
                key = ValidationPathKey.Property(name),
            )

        public fun index(index: Int): ValidationPathElement =
            ValidationPathElement(
                key = ValidationPathKey.Index(index),
            )
    }
}

public sealed interface ValidationPathKey : ValidationContext.Key<ValidationPathElement> {
    public data class Property(
        public val name: String,
    ) : ValidationPathKey

    public data class Index(
        public val index: Int,
    ) : ValidationPathKey
}

public fun ValidationContext.filterPathElements(): List<ValidationPathElement> {
    val context = this

    return buildList {
        context.fold(this) { acc, element ->
            if (element is ValidationPathElement) acc.add(element)
            acc
        }
    }
}
