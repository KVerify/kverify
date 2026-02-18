package io.github.kverify.rule.set.string

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPathElement
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class LengthRangeRule(
    public val min: Int,
    public val max: Int,
    violationFactory: ViolationFactory<String> = LengthRangeViolationFactory(min, max),
) : PredicateRule<String>(
        validationCheck = LengthRangeCheck(min, max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun LengthRangeRule(
    min: Int,
    max: Int,
    reason: String,
): LengthRangeRule =
    LengthRangeRule(
        min = min,
        max = max,
        violationFactory =
            LengthRangeViolationFactory(
                min = min,
                max = max,
                reason = reason,
            ),
    )

public class LengthRangeCheck(
    public val min: Int,
    public val max: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean {
        val length = value.length

        return (length >= min) && (length <= max)
    }
}

public data class LengthRangeViolation(
    val minLengthAllowed: Int,
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class LengthRangeViolationFactory(
    public val min: Int,
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): LengthRangeViolation {
        val actualLength = value.length
        return LengthRangeViolation(
            minLengthAllowed = min,
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be between $min and $max characters long. Actual length: $actualLength",
        )
    }
}
