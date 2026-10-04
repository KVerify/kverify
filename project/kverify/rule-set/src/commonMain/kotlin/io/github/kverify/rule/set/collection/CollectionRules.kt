package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.verification.ValueVerification

public fun <C : Collection<*>> ValueVerification<C, *>.minSize(
    minSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize < minSizeAllowed) {
        MinSizeViolation(
            minSizeAllowed = minSizeAllowed,
            actualSize = actualSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have at least $minSizeAllowed elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.maxSize(
    maxSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize > maxSizeAllowed) {
        MaxSizeViolation(
            maxSizeAllowed = maxSizeAllowed,
            actualSize = actualSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have at most $maxSizeAllowed elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.exactSize(
    expectedSize: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize != expectedSize) {
        ExactSizeViolation(
            expectedSize = expectedSize,
            actualSize = actualSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have exactly $expectedSize elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.sizeRange(
    minSizeAllowed: Int,
    maxSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize !in minSizeAllowed..maxSizeAllowed) {
        SizeRangeViolation(
            minSizeAllowed = minSizeAllowed,
            maxSizeAllowed = maxSizeAllowed,
            actualSize = actualSize,
            validationPath = validationContext.validationPath(),
            reason =
                reason
                    ?: "Collection must have between $minSizeAllowed and $maxSizeAllowed elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.distinct(reason: String? = null) {
    val actualSize = value.size

    if (actualSize < 2) return

    val distinctSize = value.toSet().size
    val duplicatesCount = actualSize - distinctSize

    validationStrategy.failIf(actualSize != distinctSize) {
        DistinctViolation(
            actualSize = actualSize,
            distinctSize = distinctSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must contain distinct elements. Found $duplicatesCount duplicates",
        )
    }
}
