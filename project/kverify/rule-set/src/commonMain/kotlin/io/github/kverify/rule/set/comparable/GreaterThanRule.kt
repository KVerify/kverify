package io.github.kverify.rule.set.comparable

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class GreaterThanCheck<T : Comparable<T>>(
    public val min: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value > min
}

public data class GreaterThanViolation<T : Comparable<T>>(
    val minExclusive: T,
    val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public class GreaterThanViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): GreaterThanViolation<T> =
        GreaterThanViolation(
            minExclusive = min,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be greater than $min. Actual: $value",
        )
}

public class GreaterThanRule<T : Comparable<T>>(
    public val min: T,
    violationFactory: ViolationFactory<T> = GreaterThanViolationFactory(min),
) : PredicateRule<T>(
        validationCheck = GreaterThanCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> GreaterThanRule(
    min: T,
    reason: String,
): GreaterThanRule<T> =
    GreaterThanRule(
        min = min,
        violationFactory =
            GreaterThanViolationFactory(
                min = min,
                reason = reason,
            ),
    )
