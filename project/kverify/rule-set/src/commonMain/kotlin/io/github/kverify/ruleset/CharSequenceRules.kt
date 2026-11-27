package io.github.kverify.ruleset

import io.github.kverify.core.ValidationPathSegment
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.failIf
import io.github.kverify.core.validationPath

public class NotBlankViolation(
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "NotBlankViolation(validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is NotBlankViolation -> {
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

public class MinLengthViolation(
    public val minLength: Int,
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "MinLengthViolation(minLength=$minLength, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is MinLengthViolation -> {
                false
            }

            else -> {
                minLength == other.minLength &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class MaxLengthViolation(
    public val maxLength: Int,
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "MaxLengthViolation(maxLength=$maxLength, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is MaxLengthViolation -> {
                false
            }

            else -> {
                maxLength == other.maxLength &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = maxLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class LengthBetweenViolation(
    public val minLength: Int,
    public val maxLength: Int,
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String =
        "LengthBetweenViolation(minLength=$minLength, maxLength=$maxLength, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is LengthBetweenViolation -> {
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

public class ExactLengthViolation(
    public val expectedLength: Int,
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "ExactLengthViolation(expectedLength=$expectedLength, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is ExactLengthViolation -> {
                false
            }

            else -> {
                expectedLength == other.expectedLength &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = expectedLength.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class MatchesPatternViolation(
    public val regex: Regex,
    override val validationPath: List<ValidationPathSegment>,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "MatchesPatternViolation(regex=${regex.pattern}, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is MatchesPatternViolation -> {
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

/**
 * Reports a [NotBlankViolation] if the value is blank.
 *
 * @param reason the reason of the reported violation, or `null` to use "Must not be blank".
 */
public fun ValueValidationScope<CharSequence>.notBlank(reason: String? = null) {
    failIf(value.isBlank()) {
        NotBlankViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must not be blank",
        )
    }
}

/**
 * Reports a [MinLengthViolation] if the length of the value is less than [minLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be at least [minLength]".
 */
public fun ValueValidationScope<CharSequence>.minLength(
    minLength: Int,
    reason: String? = null,
) {
    failIf(value.length < minLength) {
        MinLengthViolation(
            minLength = minLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be at least $minLength",
        )
    }
}

/**
 * Reports a [MaxLengthViolation] if the length of the value is greater than [maxLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be at most [maxLength]".
 */
public fun ValueValidationScope<CharSequence>.maxLength(
    maxLength: Int,
    reason: String? = null,
) {
    failIf(value.length > maxLength) {
        MaxLengthViolation(
            maxLength = maxLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be at most $maxLength",
        )
    }
}

/**
 * Reports a [LengthBetweenViolation] if the length of the value is not between [minLength]
 * and [maxLength], inclusive.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be between [minLength] and [maxLength]".
 */
public fun ValueValidationScope<CharSequence>.lengthBetween(
    minLength: Int,
    maxLength: Int,
    reason: String? = null,
) {
    failIf(value.length !in minLength..maxLength) {
        LengthBetweenViolation(
            minLength = minLength,
            maxLength = maxLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be between $minLength and $maxLength",
        )
    }
}

/**
 * Reports an [ExactLengthViolation] if the length of the value is not [expectedLength].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Length must be exactly [expectedLength]".
 */
public fun ValueValidationScope<CharSequence>.exactLength(
    expectedLength: Int,
    reason: String? = null,
) {
    failIf(value.length != expectedLength) {
        ExactLengthViolation(
            expectedLength = expectedLength,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Length must be exactly $expectedLength",
        )
    }
}

/**
 * Reports a [MatchesPatternViolation] if the value does not match [regex] entirely.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must match pattern" followed by the pattern of [regex].
 */
public fun ValueValidationScope<CharSequence>.matchesPattern(
    regex: Regex,
    reason: String? = null,
) {
    failIf(!regex.matches(value)) {
        MatchesPatternViolation(
            regex = regex,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must match pattern ${regex.pattern}",
        )
    }
}
