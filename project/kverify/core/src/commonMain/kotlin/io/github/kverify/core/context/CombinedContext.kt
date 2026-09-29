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
    override fun iterator(): Iterator<ValidationContext.Element> {
        var cur: ValidationContext = this@CombinedContext
        val rights = ArrayList<ValidationContext.Element>()

        while (cur is CombinedContext) {
            rights.add(cur.right)
            cur = cur.left
        }

        return CombinedContextIterator(cur, rights)
    }
}

private class CombinedContextIterator(
    private var leftmost: ValidationContext?,
    private val pendingRights: ArrayList<ValidationContext.Element>,
) : Iterator<ValidationContext.Element> {
    private var leftmostIterator: Iterator<ValidationContext.Element>? = null
    private var lookahead: ValidationContext.Element? = null

    override fun hasNext(): Boolean {
        while (lookahead == null) {
            val readingIterator = leftmostIterator
            val unopened = leftmost

            when {
                readingIterator != null -> {
                    if (readingIterator.hasNext()) {
                        lookahead = readingIterator.next()
                    } else {
                        leftmostIterator = null
                    }
                }

                unopened != null -> {
                    val opened = unopened.iterator()

                    leftmost = null

                    if (opened !is CombinedContextIterator) {
                        leftmostIterator = opened
                    } else {
                        leftmost = opened.leftmost
                        leftmostIterator = opened.leftmostIterator
                        lookahead = opened.lookahead
                        pendingRights.addAll(opened.pendingRights)
                    }
                }

                pendingRights.isEmpty() -> {
                    return false
                }

                else -> {
                    lookahead = pendingRights.removeAt(pendingRights.lastIndex)
                }
            }
        }

        return true
    }

    override fun next(): ValidationContext.Element {
        if (!hasNext()) throw NoSuchElementException()

        val element = checkNotNull(lookahead)

        lookahead = null

        return element
    }
}
