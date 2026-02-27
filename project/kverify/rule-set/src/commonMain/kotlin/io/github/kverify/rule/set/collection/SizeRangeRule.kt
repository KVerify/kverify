package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class SizeRangeRule<C : Collection<*>>(
    public val min: Int,
    public val max: Int,
    violationFactory: ViolationFactory<C> = SizeRangeViolationFactory(min, max),
) : PredicateRule<C>(
        validationCheck = SizeRangeCheck(min, max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun SizeRangeRule(
    min: Int,
    max: Int,
    reason: String,
): SizeRangeRule<Collection<*>> =
    SizeRangeRule(
        min = min,
        max = max,
        violationFactory =
            SizeRangeViolationFactory(
                min = min,
                max = max,
                reason = reason,
            ),
    )

public class SizeRangeCheck(
    public val min: Int,
    public val max: Int,
) : ValidationCheck<Collection<*>> {
    @Suppress("ConvertTwoComparisonsToRangeCheck")
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size >= min && value.size <= max
}

public data class SizeRangeViolation(
    val minSizeAllowed: Int,
    val maxSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

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
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Collection must have between $min and $max elements. Actual size: $actualSize",
        )
    }
}
