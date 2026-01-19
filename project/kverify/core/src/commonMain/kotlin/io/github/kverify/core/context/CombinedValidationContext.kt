package io.github.kverify.core.context

internal class CombinedValidationContext(
    private val left: ValidationContext,
    private val element: ValidationContext.Element,
) : ValidationContext {
    override fun <E : ValidationContext.Element> get(key: ValidationContext.Key<E>): E? {
        var cur = this
        while (true) {
            cur.element[key]?.let { return it }

            val next = cur.left

            if (next is CombinedValidationContext) {
                cur = next
            } else {
                return next[key]
            }
        }
    }

    override fun <R> fold(
        initial: R,
        operation: (R, ValidationContext.Element) -> R,
    ): R = operation(left.fold(initial, operation), element)

    override fun minusKey(key: ValidationContext.Key<*>): ValidationContext {
        element[key]?.let { return left }
        val newLeft = left.minusKey(key)
        return when {
            newLeft === left -> this
            newLeft === EmptyValidationContext -> element
            else -> CombinedValidationContext(newLeft, element)
        }
    }
}
