package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class CollectingValidationScope(
    @PublishedApi
    internal val violationStorage: MutableCollection<Violation> = ArrayList(),
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun onFailure(violation: Violation) {
        violationStorage.add(violation)
    }
}

public inline fun verifyWithCollecting(
    violationStorage: MutableCollection<Violation> = ArrayList(),
    validationContext: ValidationContext = EmptyValidationContext,
    block: CollectingValidationScope.() -> Unit,
): List<Violation> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return CollectingValidationScope(violationStorage, validationContext)
        .apply(block)
        .violationStorage
        .toList()
}
