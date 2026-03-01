package io.github.kverify.rules

import io.github.kverify.core.scope.ScopedVerification
import io.github.kverify.core.scope.failIf
import io.github.kverify.violations.DistinctViolation
import io.github.kverify.violations.ExactSizeViolation
import io.github.kverify.violations.MaxSizeViolation
import io.github.kverify.violations.MinSizeViolation
import io.github.kverify.violations.SizeRangeViolation

public fun <C : Collection<*>> ScopedVerification<C>.minSize(
    min: Int,
    reason: String? = null,
): ScopedVerification<C> =
    apply {
        val size = value.size
        scope.failIf(size < min) {
            MinSizeViolation(
                minSizeAllowed = min,
                actualSize = size,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have at least $min elements. Actual size: $size",
            )
        }
    }

public fun <C : Collection<*>> ScopedVerification<C>.maxSize(
    max: Int,
    reason: String? = null,
): ScopedVerification<C> =
    apply {
        val size = value.size
        scope.failIf(size > max) {
            MaxSizeViolation(
                maxSizeAllowed = max,
                actualSize = size,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have at most $max elements. Actual size: $size",
            )
        }
    }

public fun <C : Collection<*>> ScopedVerification<C>.exactSize(
    size: Int,
    reason: String? = null,
): ScopedVerification<C> =
    apply {
        val actualSize = value.size
        scope.failIf(actualSize != size) {
            ExactSizeViolation(
                expectedSize = size,
                actualSize = actualSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
            )
        }
    }

public fun <C : Collection<*>> ScopedVerification<C>.sizeRange(
    min: Int,
    max: Int,
    reason: String? = null,
): ScopedVerification<C> =
    apply {
        val size = value.size
        scope.failIf(size < min || size > max) {
            SizeRangeViolation(
                minSizeAllowed = min,
                maxSizeAllowed = max,
                actualSize = size,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must have between $min and $max elements. Actual size: $size",
            )
        }
    }

public fun <C : Collection<*>> ScopedVerification<C>.distinct(reason: String? = null): ScopedVerification<C> =
    apply {
        val size = value.size
        val distinctSize = value.toSet().size
        scope.failIf(size != distinctSize) {
            DistinctViolation(
                actualSize = size,
                distinctSize = distinctSize,
                validationPath = scope.validationContext.validationPath(),
                reason = reason ?: "Collection must contain distinct elements. Found ${size - distinctSize} duplicates",
            )
        }
    }
