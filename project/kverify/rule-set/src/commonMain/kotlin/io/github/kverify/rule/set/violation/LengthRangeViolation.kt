package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class LengthRangeViolation(
    public val minLength: Int,
    public val maxLength: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "LengthRangeViolation(minLength=$minLength, maxLength=$maxLength, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is LengthRangeViolation -> {
                false
            }

            else -> {
                minLength == other.minLength &&
                    maxLength == other.maxLength &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minLength.hashCode()
        result = 31 * result + maxLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}
