package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.collection.DistinctViolation

public class DistinctViolationFactory(
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): DistinctViolation {
        val actualSize = value.size
        val distinctSize = value.toSet().size
        return DistinctViolation(
            actualSize = actualSize,
            distinctSize = distinctSize,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must contain distinct elements. Found ${actualSize - distinctSize} duplicates",
        )
    }
}
