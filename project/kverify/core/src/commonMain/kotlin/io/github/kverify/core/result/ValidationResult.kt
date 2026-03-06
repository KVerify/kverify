package io.github.kverify.core.result

import io.github.kverify.core.exception.ValidationException
import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmInline

@JvmInline
public value class ValidationResult(
    public val violations: List<Violation>,
) {
    public inline val isValid: Boolean get() = violations.isEmpty()
    public inline val isInvalid: Boolean get() = !isValid
}

public inline fun ValidationResult.onValid(block: () -> Unit) {
    if (isValid) block()
}

public inline fun ValidationResult.onInvalid(block: (List<Violation>) -> Unit) {
    if (isInvalid) block(violations)
}

public inline fun <T> ValidationResult.fold(
    onValid: () -> T,
    onInvalid: (List<Violation>) -> T,
): T =
    if (isValid) {
        onValid()
    } else {
        onInvalid(violations)
    }

public fun ValidationResult.throwOnInvalid(): Unit =
    onInvalid {
        throw ValidationException(it)
    }
