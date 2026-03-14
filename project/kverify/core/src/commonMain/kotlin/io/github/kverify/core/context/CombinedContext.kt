package io.github.kverify.core.context

internal class CombinedContext(
    private val left: ValidationContext,
    private val right: ValidationContext.Element,
) : ValidationContext {
    override fun iterator(): Iterator<ValidationContext.Element> =
        iterator {
            var cur: ValidationContext = this@CombinedContext
            val rights = ArrayList<ValidationContext.Element>()

            while (cur is CombinedContext) {
                rights.add(cur.right)
                cur = cur.left
            }

            yieldAll(cur)

            for (i in rights.indices.reversed()) {
                yield(rights[i])
            }
        }
}
