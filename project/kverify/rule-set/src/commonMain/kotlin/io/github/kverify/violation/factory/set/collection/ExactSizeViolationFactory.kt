package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.collection.ExactSizeViolation

public class ExactSizeViolationFactory(
    public val size: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): ExactSizeViolation {
        val actualSize = value.size
        return ExactSizeViolation(
            expectedSize = size,
            actualSize = actualSize,
            validationPath = scope.validationContext.filterPathElements(),
            reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
        )
    }
}
