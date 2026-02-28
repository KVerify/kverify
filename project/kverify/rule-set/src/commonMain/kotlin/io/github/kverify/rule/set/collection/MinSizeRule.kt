package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.rule.set.validationPath
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class MinSizeRule<C : Collection<*>>(
    public val min: Int,
    violationFactory: ViolationFactory<C> = MinSizeViolationFactory(min),
) : PredicateRule<C>(
        validationCheck = MinSizeCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MinSizeRule(
    min: Int,
    reason: String,
): MinSizeRule<Collection<*>> =
    MinSizeRule(
        min = min,
        violationFactory =
            MinSizeViolationFactory(
                min = min,
                reason = reason,
            ),
    )

public class MinSizeCheck(
    public val min: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size >= min
}

public data class MinSizeViolation(
    val minSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class MinSizeViolationFactory(
    public val min: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): MinSizeViolation {
        val actualSize = value.size
        return MinSizeViolation(
            minSizeAllowed = min,
            actualSize = actualSize,
            validationPath = scope.validationContext.validationPath(),
            reason = reason ?: "Collection must have at least $min elements. Actual size: $actualSize",
        )
    }
}
