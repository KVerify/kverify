package io.github.kverify.ruleset

import io.github.kverify.core.ValidationPath
import io.github.kverify.core.ValueValidationScope
import io.github.kverify.core.ViolationWithPath
import io.github.kverify.core.failIf
import io.github.kverify.core.validationPath

/**
 * Reports a [MinSizeViolation] if the size of the value is less than [minSize].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Size must be at least [minSize]".
 */
public fun ValueValidationScope<Collection<*>>.minSize(
    minSize: Int,
    reason: String? = null,
) {
    failIf(value.size < minSize) {
        MinSizeViolation(
            minSize = minSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Size must be at least $minSize",
        )
    }
}

/**
 * Reports a [MaxSizeViolation] if the size of the value is greater than [maxSize].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Size must be at most [maxSize]".
 */
public fun ValueValidationScope<Collection<*>>.maxSize(
    maxSize: Int,
    reason: String? = null,
) {
    failIf(value.size > maxSize) {
        MaxSizeViolation(
            maxSize = maxSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Size must be at most $maxSize",
        )
    }
}

/**
 * Reports a [SizeBetweenViolation] if the size of the value is not between [minSize]
 * and [maxSize], inclusive.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Size must be between [minSize] and [maxSize]".
 */
public fun ValueValidationScope<Collection<*>>.sizeBetween(
    minSize: Int,
    maxSize: Int,
    reason: String? = null,
) {
    failIf(value.size !in minSize..maxSize) {
        SizeBetweenViolation(
            minSize = minSize,
            maxSize = maxSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Size must be between $minSize and $maxSize",
        )
    }
}

/**
 * Reports an [ExactSizeViolation] if the size of the value is not [expectedSize].
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Size must be exactly [expectedSize]".
 */
public fun ValueValidationScope<Collection<*>>.exactSize(
    expectedSize: Int,
    reason: String? = null,
) {
    failIf(value.size != expectedSize) {
        ExactSizeViolation(
            expectedSize = expectedSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Size must be exactly $expectedSize",
        )
    }
}

/**
 * Reports a [DistinctViolation] if the value contains equal elements.
 *
 * @param reason the reason of the reported violation, or `null` to use
 * "Must contain only distinct elements".
 */
public fun ValueValidationScope<Collection<*>>.distinct(reason: String? = null) {
    if (value.size < 2) return

    val initialCapacity = (value.size / 0.75).toInt() + 1
    val seen = HashSet<Any?>(initialCapacity)

    val allDistinct = value.all { seen.add(it) }

    failIf(!allDistinct) {
        DistinctViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Must contain only distinct elements",
        )
    }
}

public class MinSizeViolation(
    public val minSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "MinSizeViolation(minSize=$minSize, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is MinSizeViolation -> {
                false
            }

            else -> {
                minSize == other.minSize &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = minSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class MaxSizeViolation(
    public val maxSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "MaxSizeViolation(maxSize=$maxSize, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is MaxSizeViolation -> {
                false
            }

            else -> {
                maxSize == other.maxSize &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = maxSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class SizeBetweenViolation(
    public val minSize: Int,
    public val maxSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
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

public class ExactSizeViolation(
    public val expectedSize: Int,
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "ExactSizeViolation(expectedSize=$expectedSize, validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is ExactSizeViolation -> {
                false
            }

            else -> {
                expectedSize == other.expectedSize &&
                    validationPath == other.validationPath &&
                    reason == other.reason
            }
        }

    override fun hashCode(): Int {
        var result = expectedSize.hashCode()
        result = 31 * result + validationPath.hashCode()
        result = 31 * result + reason.hashCode()
        return result
    }
}

public class DistinctViolation(
    override val validationPath: ValidationPath,
    override val reason: String,
) : ViolationWithPath {
    override fun toString(): String = "DistinctViolation(validationPath=$validationPath, reason=$reason)"

    override fun equals(other: Any?): Boolean =
        when {
            this === other -> {
                true
            }

            other !is DistinctViolation -> {
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
