package io.github.kverify.rule.set.comparable

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPathElement
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class BetweenRule<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    violationFactory: ViolationFactory<T> = BetweenViolationFactory(min, max),
) : PredicateRule<T>(
        validationCheck = BetweenCheck(min, max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> BetweenRule(
    min: T,
    max: T,
    reason: String,
): BetweenRule<T> =
    BetweenRule(
        min = min,
        max = max,
        violationFactory =
            BetweenViolationFactory(
                min = min,
                max = max,
                reason = reason,
            ),
    )

public class BetweenCheck<T : Comparable<T>>(
    public val min: T,
    public val max: T,
) : ValidationCheck<T> {
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value >= min && value <= max
}

public data class BetweenViolation<T : Comparable<T>>(
    val min: T,
    val max: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class BetweenViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val max: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): BetweenViolation<T> =
        BetweenViolation(
            min = min,
            max = max,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be between $min and $max. Actual: $value",
        )
}
