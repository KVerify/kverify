package io.github.kverify.check.set

internal fun disjoint(
    c1: Collection<*>,
    c2: Collection<*>,
): Boolean {
    if (c1.isEmpty() || c2.isEmpty()) return true

    val iterate: Collection<*>
    val contains: Collection<*>

    when {
        c1 is Set<*> -> {
            iterate = c2
            contains = c1
        }

        c2 is Set<*> -> {
            iterate = c1
            contains = c2
        }

        c1.size > c2.size -> {
            iterate = c2
            contains = c1
        }

        else -> {
            iterate = c1
            contains = c2
        }
    }

    return iterate.none { it in contains }
}
