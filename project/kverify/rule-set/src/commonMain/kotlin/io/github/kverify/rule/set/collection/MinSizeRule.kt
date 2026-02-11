package io.github.kverify.rule.set.collection

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

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
    override val validationPath: ValidationPath,
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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have at least $min elements. Actual size: $actualSize",
        )
    }
}

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
