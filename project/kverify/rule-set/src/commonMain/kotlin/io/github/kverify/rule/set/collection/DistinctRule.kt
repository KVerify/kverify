package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.ValidationPathElement
import io.github.kverify.rule.set.validationPath
import io.github.kverify.core.rule.PredicateRule
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.rule.ValidationCheck
import io.github.kverify.core.rule.ViolationFactory
import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.PathAwareViolation

public class DistinctRule<C : Collection<*>>(
    violationFactory: ViolationFactory<C> = DistinctViolationFactory(),
) : PredicateRule<C>(
        validationCheck = DistinctCheck,
        violationFactory = violationFactory,
    ) {
    public companion object : Rule<Collection<*>> by DistinctRule()
}

@Suppress("NOTHING_TO_INLINE")
public inline fun DistinctRule(reason: String): DistinctRule<Collection<*>> =
    DistinctRule(
        violationFactory =
            DistinctViolationFactory(
                reason = reason,
            ),
    )

public object DistinctCheck : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size == value.toSet().size
}

public data class DistinctViolation(
    val actualSize: Int,
    val distinctSize: Int,
    override val validationPath: List<ValidationPathElement>,
    override val reason: String,
) : PathAwareViolation

public class DistinctViolationFactory(
    public val reason: String? = null,
) : ViolationFactory<Collection<*>> {
    override fun createViolation(
        scope: ValidationScope,
        value: Collection<*>,
    ): DistinctViolation {
        val actualSize = value.size
        val distinctSize = value.toSet().size
        return DistinctViolation(
            actualSize = actualSize,
            distinctSize = distinctSize,
            validationPath = scope.validationContext.validationPath(),
            reason =
                reason
                    ?: "Collection must contain distinct elements. Found ${actualSize - distinctSize} duplicates",
        )
    }
}
