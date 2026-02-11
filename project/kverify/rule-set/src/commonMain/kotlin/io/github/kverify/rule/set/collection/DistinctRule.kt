package io.github.kverify.rule.set.collection

import io.github.kverify.core.PredicateRule
import io.github.kverify.core.Rule
import io.github.kverify.core.ValidationCheck
import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.ViolationFactory
import io.github.kverify.core.pathElements
import io.github.kverify.rule.set.PathAwareViolation

public object DistinctCheck : ValidationCheck<Collection<*>> {
    override fun isValid(
        scope: ValidationScope,
        value: Collection<*>,
    ): Boolean = value.size == value.toSet().size
}

public data class DistinctViolation(
    val actualSize: Int,
    val distinctSize: Int,
    override val validationPath: ValidationPath,
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
            validationPath = scope.validationContext.pathElements(),
            reason = reason ?: "Collection must contain distinct elements. Found ${actualSize - distinctSize} duplicates",
        )
    }
}

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
