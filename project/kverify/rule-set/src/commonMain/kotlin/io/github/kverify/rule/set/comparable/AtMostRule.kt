package io.github.kverify.rule.set.comparable

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.context.pathElements
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.rule.set.PathAwareViolation

public class AtMostRule<T : Comparable<T>>(
    public val max: T,
    violationFactory: ViolationFactory<T> = AtMostViolationFactory(max),
) : PredicateRule<T>(
        validationCheck = AtMostCheck(max),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> AtMostRule(
    max: T,
    reason: String,
): AtMostRule<T> =
    AtMostRule(
        max = max,
        violationFactory =
            AtMostViolationFactory(
                max = max,
                reason = reason,
            ),
    )

public class AtMostCheck<T : Comparable<T>>(
    public val max: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value <= max
}

public data class AtMostViolation<T : Comparable<T>>(
    val maxAllowed: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class AtMostViolationFactory<T : Comparable<T>>(
    public val max: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): AtMostViolation<T> =
        AtMostViolation(
            maxAllowed = max,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be at most $max. Actual: $value",
        )
}
