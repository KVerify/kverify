package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.result.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

public class CollectingValidationScope(
    @PublishedApi
    internal val violationStorage: MutableCollection<Violation> = ArrayList(),
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        violationStorage.add(violation)
    }
}

public inline fun verifyCollecting(
    validationContext: ValidationContext = EmptyValidationContext,
    block: CollectingValidationScope.() -> Unit,
): ValidationResult {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    val violationStorage = ArrayList<Violation>()

    val scope = CollectingValidationScope(violationStorage, validationContext)
    scope.block()

    val violations = scope.violationStorage.toList()

    return ValidationResult(violations)
}
