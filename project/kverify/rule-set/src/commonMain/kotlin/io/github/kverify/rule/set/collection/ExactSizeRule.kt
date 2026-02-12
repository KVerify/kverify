package io.github.kverify.rule.set.collection

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

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
    override val validationPath: ValidationPath,
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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
        )
    }
}
