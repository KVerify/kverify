package io.github.kverify.violation.set.collection

import io.github.kverify.core.violation.Violation
import io.github.kverify.violation.set.joinWithLimitAndBrackets
import io.github.kverify.violation.set.resolveCollectionName

public data class CollectionContainsNoneViolation<E, C : Collection<E>>(
    val value: C,
    val elements: Collection<E>,
    val name: String? = null,
    override val reason: String =
        run {
            val displayName = resolveCollectionName(name)
            val elementsAsString = elements.joinWithLimitAndBrackets()
            val forbiddenElements = value.filter { it in elements }.joinWithLimitAndBrackets()

            "$displayName must not contain these elements: $elementsAsString, but these are present: $forbiddenElements."
        },
) : Violation {
    public constructor(
        value: C,
        vararg elements: E,
        name: String? = null,
    ) : this(
        value = value,
        elements = elements.asList(),
        name = name,
    )

    public constructor(
        value: C,
        vararg elements: E,
        name: String? = null,
        reason: String,
    ) : this(
        value = value,
        elements = elements.asList(),
        name = name,
        reason = reason,
    )
}
