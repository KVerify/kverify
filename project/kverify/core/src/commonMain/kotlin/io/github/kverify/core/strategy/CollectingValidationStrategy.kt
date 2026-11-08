package io.github.kverify.core.strategy

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.scope.ValidationScope
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
    block: ValidationScope.() -> Unit,
): List<Violation> {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return buildList {
        val strategy = CollectingValidationStrategy(this)

        ValidationScope(
            validationStrategy = strategy,
            validationContext = validationContext,
        ).apply(block)
    }
}
