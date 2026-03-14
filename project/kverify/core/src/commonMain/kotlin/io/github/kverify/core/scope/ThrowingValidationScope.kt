package io.github.kverify.core.scope

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.exception.ViolationException
import io.github.kverify.core.rule.Rule
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * A [ValidationScope] that throws a [ViolationException] on the first failed [Rule].
 */
public class ThrowingValidationScope(
    override val validationContext: ValidationContext,
) : ValidationScope {
    /**
     * Evaluates [rule] and throws a [ViolationException] immediately if it fails.
     *
     * Does nothing if [rule] returns `null`.
     *
     * @throws ViolationException if [rule] produces a violation.
     */
    override fun enforce(rule: Rule) {
        val violation = rule.check() ?: return

        throw ViolationException(violation)
    }
}

/**
 * Runs [block] in a [ThrowingValidationScope], throwing on the first violation encountered.
 *
 * If a rule fails, a [ViolationException] is thrown.
 * If [block] completes without any rule failing, its return value is returned normally.
 *
 * An optional [validationContext] can be provided to pre-seed the scope with path segments or
 * other contextual data before [block] runs.
 *
 * @param validationContext Initial context for the scope. Defaults to [EmptyValidationContext].
 * @param block The validation logic to run. Invoked exactly once.
 * @return The value returned by [block] if all rules pass.
 * @throws ViolationException on the first rule failure.
 */
public inline fun <T> validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ThrowingValidationScope.() -> T,
): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ThrowingValidationScope(validationContext).run(block)
}
