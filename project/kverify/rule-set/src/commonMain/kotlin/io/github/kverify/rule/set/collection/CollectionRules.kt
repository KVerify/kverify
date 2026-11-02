package io.github.kverify.rule.set.collection

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf

public fun <C : Collection<*>> ValueValidationScope<C>.minSize(
    minSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size

    failIf(actualSize < minSizeAllowed) {
        MinSizeViolation(
            minSizeAllowed = minSizeAllowed,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have at least $minSizeAllowed elements",
        )
    }
}

public fun <C : Collection<*>> ValueValidationScope<C>.maxSize(
    maxSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size

    failIf(actualSize > maxSizeAllowed) {
        MaxSizeViolation(
            maxSizeAllowed = maxSizeAllowed,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have at most $maxSizeAllowed elements",
        )
    }
}

public fun <C : Collection<*>> ValueValidationScope<C>.exactSize(
    expectedSize: Int,
    reason: String? = null,
) {
    val actualSize = value.size

    failIf(actualSize != expectedSize) {
        ExactSizeViolation(
            expectedSize = expectedSize,
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must have exactly $expectedSize elements",
        )
    }
}

public fun <C : Collection<*>> ValueValidationScope<C>.sizeRange(
    minSizeAllowed: Int,
    maxSizeAllowed: Int,
    reason: String? = null,
) {
    val actualSize = value.size

    failIf(actualSize !in minSizeAllowed..maxSizeAllowed) {
        SizeRangeViolation(
            minSizeAllowed = minSizeAllowed,
            maxSizeAllowed = maxSizeAllowed,
            validationPath = validationContext.validationPath(),
            reason =
                reason
                    ?: "Collection must have between $minSizeAllowed and $maxSizeAllowed elements",
        )
    }
}

public fun <C : Collection<*>> ValueValidationScope<C>.distinct(reason: String? = null) {
    val actualSize = value.size

    if (actualSize < 2) return

    val distinctSize = value.toSet().size

    failIf(actualSize != distinctSize) {
        DistinctViolation(
            validationPath = validationContext.validationPath(),
            reason = reason ?: "Collection must contain distinct elements",
        )
    }
}
