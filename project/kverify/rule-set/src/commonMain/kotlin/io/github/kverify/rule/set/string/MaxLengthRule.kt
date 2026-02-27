package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class MaxLengthRule(
    public val max: Int,
    violationFactory: ViolationFactory<String> = MaxLengthViolationFactory(max),
) : PredicateRule<String>(
        validationCheck = MaxLengthCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MaxLengthRule(
    max: Int,
    reason: String,
): MaxLengthRule =
    MaxLengthRule(
        max = max,
        violationFactory =
            MaxLengthViolationFactory(
                max = max,
                reason = reason,
            ),
    )

public class MaxLengthCheck(
    public val max: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.length <= max
}

public data class MaxLengthViolation(
    val maxLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class MaxLengthViolationFactory(
    public val max: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MaxLengthViolation {
        val actualLength = value.length
        return MaxLengthViolation(
            maxLengthAllowed = max,
            actualLength = actualLength,
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Value must be at most $max characters long. Actual length: $actualLength",
        )
    }
}
