package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class MinLengthRule(
    public val min: Int,
    violationFactory: ViolationFactory<String> = MinLengthViolationFactory(min),
) : PredicateRule<String>(
        validationCheck = MinLengthCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun MinLengthRule(
    min: Int,
    reason: String,
): MinLengthRule =
    MinLengthRule(
        min = min,
        violationFactory =
            MinLengthViolationFactory(
                min = min,
                reason = reason,
            ),
    )

public class MinLengthCheck(
    public val min: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.length >= min
}

public data class MinLengthViolation(
    val minLengthAllowed: Int,
    val actualLength: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class MinLengthViolationFactory(
    public val min: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): MinLengthViolation {
        val actualLength = value.length
        return MinLengthViolation(
            minLengthAllowed = min,
            actualLength = actualLength,
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}
