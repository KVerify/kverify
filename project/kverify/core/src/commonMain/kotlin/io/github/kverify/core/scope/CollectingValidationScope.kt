package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.result.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class CollectingValidationScope(
    private val violationStorage: MutableCollection<Violation>,
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        violationStorage.add(violation)
    }
}

public inline fun validateCollecting(
    validationContext: ValidationContext = EmptyValidationContext,
    block: CollectingValidationScope.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val violations =
        buildList {
            CollectingValidationScope(
                violationStorage = this,
                validationContext,
            ).block()
        }

    return ValidationResult(violations)
}
