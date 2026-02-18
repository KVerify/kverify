package io.github.kverify.rule.set.string

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPathElement
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at least $min characters long. Actual length: $actualLength",
        )
    }
}
