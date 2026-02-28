package io.github.kverify.core.context

internal class CombinedContext(
    private val left: ValidationContext,
    private val right: ValidationContext.Element,
) : ValidationContext {
    override fun <R> fold(
        initial: R,
        operation: (R, ValidationContext.Element) -> R,
    ): R {
        var cur: ValidationContext = this
        val rights = ArrayList<ValidationContext.Element>()

        while (cur is CombinedContext) {
            rights.add(cur.right)
            cur = cur.left
        }

        var result = cur.fold(initial, operation)

        for (i in rights.lastIndex downTo 0) {
            result = operation(result, rights[i])
        }

        return result
    }
}
