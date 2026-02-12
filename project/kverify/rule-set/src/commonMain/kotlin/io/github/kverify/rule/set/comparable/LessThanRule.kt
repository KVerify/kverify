package io.github.kverify.rule.set.comparable

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class LessThanRule<T : Comparable<T>>(
    public val max: T,
    violationFactory: ViolationFactory<T> = LessThanViolationFactory(max),
) : PredicateRule<T>(
        validationCheck = LessThanCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> LessThanRule(
    max: T,
    reason: String,
): LessThanRule<T> =
    LessThanRule(
        max = max,
        violationFactory =
            LessThanViolationFactory(
                max = max,
                reason = reason,
            ),
    )

public class LessThanCheck<T : Comparable<T>>(
    public val max: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value < max
}

public data class LessThanViolation<T : Comparable<T>>(
    val maxExclusive: T,
    val actual: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public class LessThanViolationFactory<T : Comparable<T>>(
    public val max: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): LessThanViolation<T> =
        LessThanViolation(
            maxExclusive = max,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be less than $max. Actual: $value",
        )
}
