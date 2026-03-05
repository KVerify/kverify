package io.github.kverify.rules

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.context.validationPath
import io.github.kverify.core.rule.Rule
import io.github.kverify.core.scope.Verification
import io.github.kverify.core.violation.Violation
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
        val rule = CollectionMinSizeRule(value, scope.validationContext, min, reason)
        scope.enforce(rule)
    }

public fun <C : Collection<*>, V : Verification<C>> V.maxSize(
    max: Int,
    reason: String? = null,
): V =
    apply {
        val rule = CollectionMaxSizeRule(value, scope.validationContext, max, reason)
        scope.enforce(rule)
    }

public fun <C : Collection<*>, V : Verification<C>> V.exactSize(
    size: Int,
    reason: String? = null,
): V =
    apply {
        val rule = CollectionExactSizeRule(value, scope.validationContext, size, reason)
        scope.enforce(rule)
    }

public fun <C : Collection<*>, V : Verification<C>> V.sizeRange(
    min: Int,
    max: Int,
    reason: String? = null,
): V =
    apply {
        val rule = CollectionSizeRangeRule(value, scope.validationContext, min, max, reason)
        scope.enforce(rule)
    }

public fun <C : Collection<*>, V : Verification<C>> V.distinct(reason: String? = null): V =
    apply {
        val rule = CollectionDistinctRule(value, scope.validationContext, reason)
        scope.enforce(rule)
    }

private class CollectionMinSizeRule(
    private val value: Collection<*>,
    private val context: ValidationContext,
    private val minSize: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualSize = value.size

        return if (actualSize < minSize) {
            MinSizeViolation(
                minSizeAllowed = minSize,
                actualSize = actualSize,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Collection must have at least $minSize elements. Actual size: $actualSize",
            )
        } else {
            null
        }
    }
}

private class CollectionMaxSizeRule(
    private val value: Collection<*>,
    private val context: ValidationContext,
    private val maxSize: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualSize = value.size

        return if (actualSize > maxSize) {
            MaxSizeViolation(
                maxSizeAllowed = maxSize,
                actualSize = actualSize,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Collection must have at most $maxSize elements. Actual size: $actualSize",
            )
        } else {
            null
        }
    }
}

private class CollectionExactSizeRule(
    private val value: Collection<*>,
    private val context: ValidationContext,
    private val expectedSize: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualSize = value.size

        return if (actualSize != expectedSize) {
            ExactSizeViolation(
                expectedSize = expectedSize,
                actualSize = actualSize,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Collection must have exactly $expectedSize elements. Actual size: $actualSize",
            )
        } else {
            null
        }
    }
}

private class CollectionSizeRangeRule(
    private val value: Collection<*>,
    private val context: ValidationContext,
    private val minSize: Int,
    private val maxSize: Int,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualSize = value.size

        return if (actualSize !in minSize..maxSize) {
            SizeRangeViolation(
                minSizeAllowed = minSize,
                maxSizeAllowed = maxSize,
                actualSize = actualSize,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Collection must have between $minSize and $maxSize elements. Actual size: $actualSize",
            )
        } else {
            null
        }
    }
}

private class CollectionDistinctRule(
    private val value: Collection<*>,
    private val context: ValidationContext,
    private val reason: String? = null,
) : Rule {
    override fun check(): Violation? {
        val actualSize = value.size
        val distinctSize = value.toSet().size

        val duplicatesCount = actualSize - distinctSize

        return if (actualSize != distinctSize) {
            DistinctViolation(
                actualSize = actualSize,
                distinctSize = distinctSize,
                validationPath = context.validationPath(),
                reason =
                    reason
                        ?: "Collection must contain distinct elements. Found $duplicatesCount duplicates",
            )
        } else {
            null
        }
    }
}
