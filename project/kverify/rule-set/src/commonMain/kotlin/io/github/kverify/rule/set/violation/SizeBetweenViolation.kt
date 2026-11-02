package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class SizeBetweenViolation(
    public val minSize: Int,
    public val maxSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String =
        "SizeBetweenViolation(minSize=$minSize, maxSize=$maxSize, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is SizeBetweenViolation -> {
                false
            }

            else -> {
                minSize == other.minSize &&
                    maxSize == other.maxSize &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minSize.hashCode()
        result = 31 * result + maxSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}
