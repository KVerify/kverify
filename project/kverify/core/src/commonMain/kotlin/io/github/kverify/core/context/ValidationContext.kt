package io.github.kverify.core.context

public interface ValidationContext {
    public operator fun <E : Element> get(key: Key<E>): E?

    public fun <R> fold(
        initial: R,
        operation: (R, Element) -> R,
    ): R

    public operator fun plus(context: ValidationContext): ValidationContext {
        if (context === EmptyValidationContext) return this

        return context.fold(this) { acc, element ->
            val removed = acc.minusKey(element.key)

            if (removed === EmptyValidationContext) return@fold element

            CombinedValidationContext(
                left = removed,
                element = element,
            )
        }
    }

    public fun minusKey(key: Key<*>): ValidationContext

    public interface Key<E : Element>

    public interface Element : ValidationContext {
        public val key: Key<*>

        @Suppress("UNCHECKED_CAST")
        public override operator fun <E : Element> get(key: Key<E>): E? =
            if (this.key == key) {
                this as E
            } else {
                null
            }

        public override fun <R> fold(
            initial: R,
            operation: (R, Element) -> R,
        ): R = operation(initial, this)

        public override fun minusKey(key: Key<*>): ValidationContext =
            if (this.key == key) {
                EmptyValidationContext
            } else {
                this
            }
    }
}
