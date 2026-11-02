package io.github.kverify.rule.set.violation

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.PathAwareViolation

public class PatternViolation(
    public val regex: Regex,
    override val validationPath: ValidationPath,
    override val reason: String,
) : PathAwareViolation {
    override fun toString(): String = "PatternViolation(regex=$regex, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is PatternViolation -> {
                false
            }

            else -> {
                regex.pattern == other.regex.pattern &&
                    regex.options == other.regex.options &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = regex.pattern.hashCode()
        result = 31 * result + regex.options.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}
