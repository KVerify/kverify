package io.github.kverify.rule.set.enumeration

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.core.context.pathElements
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.rule.set.PathAwareViolation

public class OneOfRule<T>(
    public val allowed: Set<T>,
    violationFactory: ViolationFactory<T> = OneOfViolationFactory(allowed),
) : PredicateRule<T>(
        validationCheck = OneOfCheck(allowed),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T> OneOfRule(
    allowed: Set<T>,
    reason: String,
): OneOfRule<T> =
    OneOfRule(
        allowed = allowed,
        violationFactory =
            OneOfViolationFactory(
                allowed = allowed,
                reason = reason,
            ),
    )

public class OneOfCheck<T>(
    public val allowed: Set<T>,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value in allowed
}

public data class OneOfViolation<T>(
    val allowed: Set<T>,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class OneOfViolationFactory<T>(
    public val allowed: Set<T>,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): OneOfViolation<T> =
        OneOfViolation(
            allowed = allowed,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be one of $allowed. Actual: $value",
        )
}
