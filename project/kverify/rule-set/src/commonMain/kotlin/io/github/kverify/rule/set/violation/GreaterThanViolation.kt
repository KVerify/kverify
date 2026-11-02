package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class GreaterThanViolation<T : Comparable<T>>(
    public val minExclusive: T,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "GreaterThanViolation(minExclusive=$minExclusive, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is GreaterThanViolation<*> -> {
                false
            }

            else -> {
                minExclusive == other.minExclusive &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minExclusive.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}
