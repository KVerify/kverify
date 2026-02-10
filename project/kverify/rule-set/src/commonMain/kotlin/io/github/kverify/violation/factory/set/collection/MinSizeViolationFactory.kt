package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.collection.MinSizeViolation

public class MinSizeViolationFactory(
    public val min: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): MinSizeViolation {
        val actualSize = value.size
        return MinSizeViolation(
            minSizeAllowed = min,
            actualSize = actualSize,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have at least $min elements. Actual size: $actualSize",
        )
    }
}
