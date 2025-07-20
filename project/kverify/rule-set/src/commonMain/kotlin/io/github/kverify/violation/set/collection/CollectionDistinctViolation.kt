package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionDistinctViolation<C : Collection<*>>(
    val value: C,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)
            val duplicates =
                value
                    .groupingBy { it }
                    .eachCount()
                    .filter { it.value > 1 }
                    .keys
                    .joinWithLimitAndBrackets()

            "$displayName must be distinct, but it has duplicates: $duplicates"
        },
) : Violation
