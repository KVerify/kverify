package io.github.kverify.rule.set.comparable

import io.github.kverify.context.ValidationPathElement
import io.github.kverify.context.pathElements
import io.github.kverify.rule.PredicateRule
import io.github.kverify.rule.ValidationCheck
import io.github.kverify.rule.ViolationFactory
import io.github.kverify.rule.set.PathAwareViolation
import io.github.kverify.scope.ValidationScope

public class EqualToRule<T : Comparable<T>>(
    public val expected: T,
    violationFactory: ViolationFactory<T> = EqualToViolationFactory(expected),
) : PredicateRule<T>(
        validationCheck = EqualToCheck(expected),
        violationFactory = violationFactory,
    )

@Suppress("NOTHING_TO_INLINE")
public inline fun <T : Comparable<T>> EqualToRule(
    expected: T,
    reason: String,
): EqualToRule<T> =
    EqualToRule(
        expected = expected,
        violationFactory =
            EqualToViolationFactory(
                expected = expected,
                reason = reason,
            ),
    )

public class EqualToCheck<T : Comparable<T>>(
    public val expected: T,
) : ValidationCheck<T> {
    override fun isValid(
        scope: ValidationScope,
        value: T,
    ): Boolean = value == expected
}

public data class EqualToViolation<T : Comparable<T>>(
    val expected: T,
    val actual: T,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class EqualToViolationFactory<T : Comparable<T>>(
    public val expected: T,
    public val reason: String? = null,
) : ViolationFactory<T> {
    override fun createViolation(
        scope: ValidationScope,
        value: T,
    ): EqualToViolation<T> =
        EqualToViolation(
            expected = expected,
            actual = value,
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Value must be equal to $expected. Actual: $value",
        )
}
