package io.github.kverify.violation.factory.set.collection

import io.github.kverify.core.model.pathElements
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.collection.MaxSizeViolation

public class MaxSizeViolationFactory(
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): MaxSizeViolation {
        val actualSize = value.size
        return MaxSizeViolation(
            maxSizeAllowed = max,
            actualSize = actualSize,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have at most $max elements. Actual size: $actualSize",
        )
    }
}
