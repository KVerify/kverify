package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.ValidationResult
import io.github.kverify.core.verification.VerificationScope
import io.github.kverify.core.verification.using
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class CollectingValidationStrategy(
    private val violationStorage: MutableCollection<Violation>,
) : ValidationStrategy {
    override fun failWith(violation: Violation) {
        violationStorage.add(violation)
    }
}

public inline fun validateCollecting(
    validationContext: ValidationContext = EmptyValidationContext,
    block: context(CollectingValidationStrategy, ValidationContext) VerificationScope<CollectingValidationStrategy>.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val violations =
        buildList {
            VerificationScope(
                validationStrategy = CollectingValidationStrategy(this),
                validationContext = validationContext,
            ).using(block)
        }

    return ValidationResult(violations)
}
