package io.github.kverify.rule.set.collection

import io.github.kverify.core.model.toList
import io.github.kverify.core.strategy.failIf
import io.github.kverify.core.verification.ValueVerification

public fun <C : Collection<*>> ValueVerification<C, *>.minSize(
    min: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize < min) {
        MinSizeViolation(
            minSizeAllowed = min,
            actualSize = actualSize,
            validationPath = validationPath.toList(),
            reason = reason ?: "Collection must have at least $min elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.maxSize(
    max: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize > max) {
        MaxSizeViolation(
            maxSizeAllowed = max,
            actualSize = actualSize,
            validationPath = validationPath.toList(),
            reason = reason ?: "Collection must have at most $max elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.exactSize(
    size: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize != size) {
        ExactSizeViolation(
            expectedSize = size,
            actualSize = actualSize,
            validationPath = validationPath.toList(),
            reason = reason ?: "Collection must have exactly $size elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.sizeRange(
    min: Int,
    max: Int,
    reason: String? = null,
) {
    val actualSize = value.size
    validationStrategy.failIf(actualSize !in min..max) {
        SizeRangeViolation(
            minSizeAllowed = min,
            maxSizeAllowed = max,
            actualSize = actualSize,
            validationPath = validationPath.toList(),
            reason = reason ?: "Collection must have between $min and $max elements. Actual size: $actualSize",
        )
    }
}

public fun <C : Collection<*>> ValueVerification<C, *>.distinct(reason: String? = null) {
    val actualSize = value.size
    val distinctSize = value.toSet().size
    val duplicatesCount = actualSize - distinctSize

    validationStrategy.failIf(actualSize != distinctSize) {
        DistinctViolation(
            actualSize = actualSize,
            distinctSize = distinctSize,
            validationPath = validationPath.toList(),
            reason = reason ?: "Collection must contain distinct elements. Found $duplicatesCount duplicates",
        )
    }
}
