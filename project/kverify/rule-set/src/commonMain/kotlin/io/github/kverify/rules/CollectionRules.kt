package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.failIf
import io.github.kverify.core.verification.Verification
import io.github.kverify.violations.DistinctViolation
import io.github.kverify.violations.ExactSizeViolation
import io.github.kverify.violations.MaxSizeViolation
import io.github.kverify.violations.MinSizeViolation
import io.github.kverify.violations.SizeRangeViolation

public fun <C : Collection<*>, V : Verification<C>> V.minSize(
    min: Int,
    reason: String? = null,
): V =
    apply {
        val actualSize = value.size
        scope.failIf({ actualSize < min }) {
            MinSizeViolation(
                minSizeAllowed = min,
                actualSize = actualSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have at least $min elements. Actual size: $actualSize",
            )
        }
    }

public fun <C : Collection<*>, V : Verification<C>> V.maxSize(
    max: Int,
    reason: String? = null,
): V =
    apply {
        val actualSize = value.size
        scope.failIf({ actualSize > max }) {
            MaxSizeViolation(
                maxSizeAllowed = max,
                actualSize = actualSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have at most $max elements. Actual size: $actualSize",
            )
        }
    }

public fun <C : Collection<*>, V : Verification<C>> V.exactSize(
    size: Int,
    reason: String? = null,
): V =
    apply {
        val actualSize = value.size
        scope.failIf({ actualSize != size }) {
            ExactSizeViolation(
                expectedSize = size,
                actualSize = actualSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
            )
        }
    }

public fun <C : Collection<*>, V : Verification<C>> V.sizeRange(
    min: Int,
    max: Int,
    reason: String? = null,
): V =
    apply {
        val actualSize = value.size
        scope.failIf({ actualSize !in min..max }) {
            SizeRangeViolation(
                minSizeAllowed = min,
                maxSizeAllowed = max,
                actualSize = actualSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have between $min and $max elements. Actual size: $actualSize",
            )
        }
    }

public fun <C : Collection<*>, V : Verification<C>> V.distinct(reason: String? = null): V =
    apply {
        val actualSize = value.size
        val distinctSize = value.toSet().size
        val duplicatesCount = actualSize - distinctSize

        scope.failIf({ actualSize != distinctSize }) {
            DistinctViolation(
                actualSize = actualSize,
                distinctSize = distinctSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must contain distinct elements. Found $duplicatesCount duplicates",
            )
        }
    }
