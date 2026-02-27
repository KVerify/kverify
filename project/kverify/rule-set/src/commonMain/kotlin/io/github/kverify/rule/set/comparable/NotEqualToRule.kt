package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class NotEqualToRule<T : Comparable<T>>(
    public val forbidden: T,
    violationFactory: ViolationFactory<T> = NotEqualToViolationFactory(forbidden),
) : PredicateRule<T>(
        validationCheck = NotEqualToCheck(forbidden),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> NotEqualToRule(
    forbidden: T,
    reason: String,
): NotEqualToRule<T> =
    NotEqualToRule(
        forbidden = forbidden,
        violationFactory =
            NotEqualToViolationFactory(
                forbidden = forbidden,
                reason = reason,
            ),
    )

public class NotEqualToCheck<T : Comparable<T>>(
    public val forbidden: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value != forbidden
}

public data class NotEqualToViolation<T : Comparable<T>>(
    val forbidden: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class NotEqualToViolationFactory<T : Comparable<T>>(
    public val forbidden: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NotEqualToViolation<T> =
        NotEqualToViolation(
            forbidden = forbidden,
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Value must not be equal to $forbidden",
        )
}
