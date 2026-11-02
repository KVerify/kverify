package io.github.kverify.rule.set

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.rule.set.violation.NotNullViolation

public fun ValueValidationScope<Any?>.notNull(reason: String? = null) {
    failIf(value == null) {
        NotNullViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must not be null",
        )
    }
}
