package io.github.kverify.core.strategy

import io.github.kverify.core.violation.Violation
import kotlin.jvm.JvmName

public interface ValidationStrategy {
    public fun failWith(violation: Violation)
}

context(validationStrategy: ValidationStrategy)
public fun failWith(violation: Violation): Unit = validationStrategy.failWith(violation)

@JvmName("failIfExtension")
public inline fun ValidationStrategy.failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
) {
    if (condition) {
        val violation = lazyViolation()

        failWith(violation)
    }
}

context(validationStrategy: ValidationStrategy)
public inline fun failIf(
    condition: Boolean,
    lazyViolation: () -> Violation,
): Unit = validationStrategy.failIf(condition, lazyViolation)
