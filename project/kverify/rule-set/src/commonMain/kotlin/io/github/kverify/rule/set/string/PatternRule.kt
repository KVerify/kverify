package io.github.kverify.rule.set.string

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class PatternRule(
    public val regex: Regex,
    violationFactory: ViolationFactory<String> = PatternViolationFactory(regex),
) : PredicateRule<String>(
        validationCheck = PatternCheck(regex),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun PatternRule(
    regex: Regex,
    reason: String,
): PatternRule =
    PatternRule(
        regex = regex,
        violationFactory =
            PatternViolationFactory(
                regex = regex,
                reason = reason,
            ),
    )

public class PatternCheck(
    public val regex: Regex,
) : ValidationCheck<String> {
    override fun isValid(
        scope: ValidationScope,
        value: String,
    ): Boolean = value.matches(regex)
}

public data class PatternViolation(
    val regex: Regex,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class PatternViolationFactory(
    public val regex: Regex,
    public val reason: String? = null,
) : ViolationFactory<String> {
    override fun createViolation(
        scope: ValidationScope,
        value: String,
    ): PatternViolation =
        PatternViolation(
            regex = regex,
            validationPath = scope.validationContext.elements.filterIsInstance<ValidationPathElement>(),
            reason = reason ?: "Value must match the pattern: $regex",
        )
}
