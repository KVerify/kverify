package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.context.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
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
            validationPath = scope.validationContext.filterPathElements(),
            reason = reason ?: "Collection must contain distinct elements. Found ${actualSize - distinctSize} duplicates",
        )
    }
}
