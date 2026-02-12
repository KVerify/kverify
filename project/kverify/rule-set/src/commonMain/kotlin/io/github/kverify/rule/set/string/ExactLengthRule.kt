package io.github.kverify.rule.set.string

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public class ExactLengthRule(
    public val length: Int,
    violationFactory: ViolationFactory<String> = ExactLengthViolationFactory(length),
) : PredicateRule<String>(
        validationCheck = ExactLengthCheck(length),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun ExactLengthRule(
    length: Int,
    reason: String,
): ExactLengthRule =
    ExactLengthRule(
        length = length,
        violationFactory =
            ExactLengthViolationFactory(
                length = length,
                reason = reason,
            ),
    )

public class ExactLengthCheck(
    public val length: Int,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.length == length
}

public data class ExactLengthViolation(
    val expectedLength: Int,
    val actualLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation

public class ExactLengthViolationFactory(
    public val length: Int,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): ExactLengthViolation {
        val actualLength = value.length
        return ExactLengthViolation(
            expectedLength = length,
            actualLength = actualLength,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be exactly $length characters long. Actual length: $actualLength",
        )
    }
}
