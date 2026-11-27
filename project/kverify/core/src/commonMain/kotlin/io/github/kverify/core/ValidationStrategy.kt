package io.github.kverify.core

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Defines what happens when a violation is reported.
 */
public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

/**
 * A [ValidationStrategy] that adds every reported violation to [violationStorage].
 */
public class CollectingValidationStrategy(
    private val violationStorage: MutableCollection<Violation>,
) : ValidationStrategy {
    override fun failWith(violation: Violation) {
        violationStorage.add(violation)
    }
}

/**
 * A [ValidationStrategy] that throws a [ViolationException] with the reported violation.
 */
public object ThrowingValidationStrategy : ValidationStrategy {
    override fun failWith(violation: Violation): Nothing = throw ViolationException(violation)
}

/**
 * Calls [block] in a [ValidationScope] with the given [validationContext] and returns
 * the violations reported in that scope.
 */
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

/**
 * Calls [block] in a [ValidationScope] with the given [validationContext] and returns
 * the result of [block].
 * A violation reported in that scope is thrown as a [ViolationException].
 */
public inline fun <R> validateThrowing(
    validationContext: ValidationContext = EmptyValidationContext,
    block: ValidationScope.() -> R,
): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }

    return ValidationScope(
        validationStrategy = ThrowingValidationStrategy,
        validationContext = validationContext,
    ).run(block)
}
