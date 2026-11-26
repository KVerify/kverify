package io.github.kverify.ruleset

import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.failIf
import io.github.kverify.core.validationPath

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

public class NotNullViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "NotNullViolation(validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is NotNullViolation -> {
                false
            }

            else -> {
                validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}
