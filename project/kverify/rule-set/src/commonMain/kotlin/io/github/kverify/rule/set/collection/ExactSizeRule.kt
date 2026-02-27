package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class ExactSizeRule<C : Collection<*>>(
    public val size: Int,
    violationFactory: ViolationFactory<C> = ExactSizeViolationFactory(size),
) : PredicateRule<C>(
        validationCheck = ExactSizeCheck(size),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ExactSizeRule(
    size: Int,
    reason: String,
): ExactSizeRule<Collection<*>> =
    ExactSizeRule(
        size = size,
        violationFactory =
            ExactSizeViolationFactory(
                size = size,
                reason = reason,
            ),
    )

public class ExactSizeCheck(
    public val size: Int,
) : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size == size
}

public data class ExactSizeViolation(
    val expectedSize: Int,
    val actualSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class ExactSizeViolationFactory(
    public val size: Int,
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): ExactSizeViolation {
        val actualSize = value.size
        return ExactSizeViolation(
            expectedSize = size,
            actualSize = actualSize,
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
        )
    }
}
