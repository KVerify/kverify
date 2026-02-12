package io.github.kverify.rule.set.comparable

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

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
    override val validationPath: ValidationPath,
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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must not be equal to $forbidden",
        )
}
