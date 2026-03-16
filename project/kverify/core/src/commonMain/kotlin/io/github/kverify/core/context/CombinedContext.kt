package io.github.kverify.core.context

/**
 * A [ValidationContext] that lazily links two contexts into a left-leaning chain.
 */
internal class CombinedContext(
    private val left: ValidationContext,
    private val right: ValidationContext.Element,
) : ValidationContext {
    /**
     * Traverses the left spine iteratively rather than recursively to avoid stack overflow
     * on deeply nested chains, collecting right-hand elements along the way,
     * then emits them in insertion order.
     */
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
