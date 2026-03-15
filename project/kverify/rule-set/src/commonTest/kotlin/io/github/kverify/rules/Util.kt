package io.github.kverify.rules

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.NamePathElement
import io.github.kverify.core.scope.CollectingValidationScope
import io.github.kverify.core.scope.ThrowingValidationScope
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation

fun <T> collecting(
    value: T,
    pathElement: NamePathElement? = null,
): Pair<MutableList<Violation>, Verification<T>> {
    val storage = mutableListOf<Violation>()
    val context = pathElement ?: EmptyValidationContext
    val scope = CollectingValidationScope(storage, context)
    return storage to Verification(value, scope)
}

fun <T> throwing(value: T): Verification<T> =
    Verification(
        value,
        ThrowingValidationScope(EmptyValidationContext),
    )
