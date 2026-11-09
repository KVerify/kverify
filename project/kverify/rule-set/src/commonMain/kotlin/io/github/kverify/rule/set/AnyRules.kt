package io.github.kverify.rule.set

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.rule.set.violation.NotNullViolation

/**
 * Reports a [NotNullViolation] if the value is `null`.
 *
 * @param reason the reason of the reported violation, or `null` to use "Must not be null".
 */
public fun ValueValidationScope<Any?>.notNull(reason: String? = null) {
    failIf(value == null) {
        NotNullViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must not be null",
        )
    }
}
