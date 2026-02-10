package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.violation.set.collection.SizeRangeViolation

public class SizeRangeViolationFactory(
    public val min: Int,
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): SizeRangeViolation {
        val actualSize = value.size
        return SizeRangeViolation(
            minSizeAllowed = min,
            maxSizeAllowed = max,
            actualSize = actualSize,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have between $min and $max elements. Actual size: $actualSize",
        )
    }
}
