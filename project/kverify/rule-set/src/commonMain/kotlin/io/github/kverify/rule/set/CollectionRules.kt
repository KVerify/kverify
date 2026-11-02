package io.github.kverify.rule.set

import io.github.kverify.core.context.validationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.scope.failIf
import io.github.kverify.rule.set.violation.DistinctViolation
import io.github.kverify.rule.set.violation.ExactSizeViolation
import io.github.kverify.rule.set.violation.MaxSizeViolation
import io.github.kverify.rule.set.violation.MinSizeViolation
import io.github.kverify.rule.set.violation.SizeBetweenViolation

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
