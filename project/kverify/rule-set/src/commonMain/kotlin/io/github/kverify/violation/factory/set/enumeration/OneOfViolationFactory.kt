package io.github.kverify.violation.factory.set.enumeration

import io.github.kverify.core.context.element.filterPathElements
import io.github.kverify.core.rule.predicate.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.violation.set.enumeration.OneOfViolation

public class OneOfViolationFactory<T>(
    public val allowed: Set<T>,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): OneOfViolation<T> =
        OneOfViolation(
            allowed = allowed,
            actual = value,
            validationPath = scope.validationContext.filterPathElements(),
        )
}
