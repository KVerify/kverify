package io.github.kverify.rule.set.enumeration

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPathElement
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class NoneOfRule<T>(
    public val forbidden: Set<T>,
    violationFactory: ViolationFactory<T> = NoneOfViolationFactory(forbidden),
) : PredicateRule<T>(
        validationCheck = NoneOfCheck(forbidden),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> NoneOfRule(
    forbidden: Set<T>,
    reason: String,
): NoneOfRule<T> =
    NoneOfRule(
        forbidden = forbidden,
        violationFactory =
            NoneOfViolationFactory(
                forbidden = forbidden,
                reason = reason,
            ),
    )

public class NoneOfCheck<T>(
    public val forbidden: Set<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value !in forbidden
}

public data class NoneOfViolation<T>(
    val forbidden: Set<T>,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class NoneOfViolationFactory<T>(
    public val forbidden: Set<T>,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): NoneOfViolation<T> =
        NoneOfViolation(
            forbidden = forbidden,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be one of $forbidden. Actual: $value",
        )
}
