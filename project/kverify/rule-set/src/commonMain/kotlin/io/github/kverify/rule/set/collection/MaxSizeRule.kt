package io.github.kverify.rule.set.collection

import io.github.kverify.context.ValidationPathElement
import io.github.kverify.context.pathElements
import io.github.kverify.rule.PredicateRule
import io.github.kverify.rule.ValidationCheck
import io.github.kverify.rule.ViolationFactory
import io.github.kverify.rule.set.PathAwareViolation
import io.github.kverify.scope.ValidationScope

public class MaxSizeRule<C : Collection<*>>(
    public val max: Int,
    violationFactory: ViolationFactory<C> = MaxSizeViolationFactory(max),
) : PredicateRule<C>(
        validationCheck = MaxSizeCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MaxSizeRule(
    max: Int,
    reason: String,
): MaxSizeRule<Collection<*>> =
    MaxSizeRule(
        max = max,
        violationFactory =
            MaxSizeViolationFactory(
                max = max,
                reason = reason,
            ),
    )

public class MaxSizeCheck(
    public val max: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size <= max
}

public data class MaxSizeViolation(
    val maxSizeAllowed: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class MaxSizeViolationFactory(
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): MaxSizeViolation {
        val actualSize = value.size
        return MaxSizeViolation(
            maxSizeAllowed = max,
            actualSize = actualSize,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have at most $max elements. Actual size: $actualSize",
        )
    }
}
