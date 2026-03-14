package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.result.ValidationResult
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationScope] that collects all violations into [violationStorage].
 */
public class CollectingValidationScope(
    private val violationStorage: MutableCollection<Violation>,
    override val validationContext: ValidationContext = EmptyValidationContext,
) : ValidationScope {
    /**
     * Evaluates [rule] and appends the resulting [Violation] to [violationStorage] if it fails.
     *
     * Does nothing if [rule] returns `null`.
     */
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        violationStorage.add(violation)
    }
}

/**
 * Runs [block] in a [CollectingValidationScope] and returns a [ValidationResult] with all violations produced.
 *
 * All rules in [block] are evaluated unconditionally — a failed rule does not prevent subsequent
 * rules from running.
 *
 * An optional [validationContext] can be provided to pre-seed the scope with path segments or
 * other contextual data before [block] runs.
 *
 * @param validationContext Initial context for the scope. Defaults to [EmptyValidationContext].
 * @param block The validation logic to run. Invoked exactly once.
 * @return A [ValidationResult] containing all violations produced by [block],
 * or a valid result if all rules passed.
 */
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
