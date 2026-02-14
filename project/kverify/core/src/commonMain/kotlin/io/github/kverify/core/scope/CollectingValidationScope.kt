package io.github.kverify.core.scope

import io.github.kverify.core.EmptyValidationContext
import io.github.kverify.core.ValidationContext
import io.github.kverify.core.ValidationResult
import io.github.kverify.core.ValidationScope
import io.github.kverify.core.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public open class CollectingValidationScope(
    protected val violationStorage: MutableCollection<Violation> = ArrayList(),
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun onFailure(violation: Violation) {
        violationStorage.add(violation)
    }

    public open fun build(): ValidationResult =
        ValidationResult(
            violationStorage.toList(),
        )
}

public inline fun verifyWithCollecting(
    violationStorage: MutableCollection<Violation> = ArrayList(),
    validationContext: ValidationContext = EmptyValidationContext,
    block: CollectingValidationScope.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return CollectingValidationScope(violationStorage, validationContext)
        .apply(block)
        .build()
}
