package io.github.kverify.rules

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.scope.CollectingValidationScope
import io.github.kverify.core.scope.ThrowingValidationScope
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation

fun <T> collecting(
    value: T,
    context: ValidationContext = EmptyValidationContext,
): Pair<MutableList<Violation>, Verification<T>> {
    val storage = mutableListOf<Violation>()
    val scope = CollectingValidationScope(storage, context)
    return storage to Verification(value, scope)
}

fun <T> throwing(value: T): Verification<T> =
    Verification(
        value,
        ThrowingValidationScope(EmptyValidationContext),
    )
