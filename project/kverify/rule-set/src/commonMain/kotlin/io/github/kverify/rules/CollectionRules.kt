package io.github.kverify.rules

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.DistinctViolation
import io.github.kverify.violations.ExactSizeViolation
import io.github.kverify.violations.MaxSizeViolation
import io.github.kverify.violations.MinSizeViolation
import io.github.kverify.violations.SizeRangeViolation

/**
 * Fails with [MinSizeViolation] if the collection has fewer than [min] elements (inclusive).
 *
 * If [reason] is `null`, defaults to: `"Collection must have at least $min elements. Actual size: $actualSize"`.
 */
public fun <C : Collection<*>> Verification<C>.minSize(
    min: Int,
    reason: String? = null,
): Verification<C> =
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

/**
 * Fails with [MaxSizeViolation] if the collection has more than [max] elements (inclusive).
 *
 * If [reason] is `null`, defaults to: `"Collection must have at most $max elements. Actual size: $actualSize"`.
 */
public fun <C : Collection<*>> Verification<C>.maxSize(
    max: Int,
    reason: String? = null,
): Verification<C> =
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

/**
 * Fails with [ExactSizeViolation] if the collection size does not equal [size].
 *
 * If [reason] is `null`, defaults to: `"Collection must have exactly $size elements. Actual size: $actualSize"`.
 */
public fun <C : Collection<*>> Verification<C>.exactSize(
    size: Int,
    reason: String? = null,
): Verification<C> =
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

/**
 * Fails with [SizeRangeViolation] if the collection size is outside the range [[min], [max]] (both inclusive).
 *
 * If [reason] is `null`, defaults to: `"Collection must have between $min and $max elements. Actual size: $actualSize"`.
 */
public fun <C : Collection<*>> Verification<C>.sizeRange(
    min: Int,
    max: Int,
    reason: String? = null,
): Verification<C> =
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

/**
 * Fails with [DistinctViolation] if the collection contains duplicate elements.
 *
 * If [reason] is `null`, defaults to: `"Collection must contain distinct elements. Found $duplicatesCount duplicates"`.
 */
public fun <C : Collection<*>> Verification<C>.distinct(reason: String? = null): Verification<C> =
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
