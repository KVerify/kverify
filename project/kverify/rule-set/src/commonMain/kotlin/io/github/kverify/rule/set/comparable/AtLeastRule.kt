package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.rule.set.validationPath
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class AtLeastRule<T : Comparable<T>>(
    public val min: T,
    violationFactory: ViolationFactory<T> = AtLeastViolationFactory(min),
) : PredicateRule<T>(
        validationCheck = AtLeastCheck(min),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> AtLeastRule(
    min: T,
    reason: String,
): AtLeastRule<T> =
    AtLeastRule(
        min = min,
        violationFactory =
            AtLeastViolationFactory(
                min = min,
                reason = reason,
            ),
    )

public class AtLeastCheck<T : Comparable<T>>(
    public val min: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value >= min
}

public data class AtLeastViolation<T : Comparable<T>>(
    val minAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class AtLeastViolationFactory<T : Comparable<T>>(
    public val min: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): AtLeastViolation<T> =
        AtLeastViolation(
            minAllowed = min,
            actual = value,
            validationPath = scope.validationContext.validationPath(),
            reason = reason ?: "Value must be at least $min. Actual: $value",
        )
}
