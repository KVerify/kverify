package io.github.kverify.rule.set.util

import io.github.kverify.core.context.EmptyValidationContext
import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.NamePathSegment
import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.scope.ValueValidationScope
import io.github.kverify.core.strategy.validateCollecting
import io.github.kverify.core.violation.PathAwareViolation
import io.github.kverify.core.violation.Violation
import io.github.kverify.rule.set.violation.AtLeastViolation
import io.github.kverify.rule.set.violation.AtMostViolation
import io.github.kverify.rule.set.violation.BetweenViolation
import io.github.kverify.rule.set.violation.DistinctViolation
import io.github.kverify.rule.set.violation.ExactLengthViolation
import io.github.kverify.rule.set.violation.ExactSizeViolation
import io.github.kverify.rule.set.violation.GreaterThanViolation
import io.github.kverify.rule.set.violation.LengthRangeViolation
import io.github.kverify.rule.set.violation.LessThanViolation
import io.github.kverify.rule.set.violation.MaxLengthViolation
import io.github.kverify.rule.set.violation.MaxSizeViolation
import io.github.kverify.rule.set.violation.MinLengthViolation
import io.github.kverify.rule.set.violation.MinSizeViolation
import io.github.kverify.rule.set.violation.NotBlankViolation
import io.github.kverify.rule.set.violation.NotNullViolation
import io.github.kverify.rule.set.violation.PatternViolation
import io.github.kverify.rule.set.violation.SizeBetweenViolation
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

/**
 * Runs [rule] on a scope for [value] and returns the reported violations.
 */
internal fun <V> violationsFor(
    value: V,
    validationContext: ValidationContext = EmptyValidationContext,
    rule: ValueValidationScope<V>.() -> Unit,
): List<Violation> =
    validateCollecting(validationContext) {
        val valueScope = forValue(value)

        valueScope.rule()
    }

/**
 * Runs [rule] on a scope for [value] and returns the only reported violation.
 */
internal fun <V> violationFor(
    value: V,
    validationContext: ValidationContext = EmptyValidationContext,
    rule: ValueValidationScope<V>.() -> Unit,
): Violation {
    val violations = violationsFor(value, validationContext, rule)

    return violations.single()
}

internal fun assertNoViolations(violations: List<Violation>) {
    assertTrue(violations.isEmpty(), "Expected no violations, but got $violations")
}

internal fun assertViolationPath(
    expectedPath: ValidationPath,
    violation: Violation,
) {
    val pathAwareViolation = assertIs<PathAwareViolation>(violation)

    assertEquals(expectedPath, pathAwareViolation.validationPath)
}

internal fun namePath(name: String): ValidationPath {
    val segment = NamePathSegment(name)

    return ValidationPath(listOf(segment))
}

val somePath = ValidationPath(listOf(NamePathSegment("field")))

val otherPath = ValidationPath(listOf(NamePathSegment("other")))

fun atLeastViolation(
    min: Int = 1,
    path: ValidationPath = somePath,
    reason: String = "reason",
): AtLeastViolation<Int> = AtLeastViolation(min = min, validationPath = path, reason = reason)

fun atMostViolation(
    max: Int = 1,
    path: ValidationPath = somePath,
    reason: String = "reason",
): AtMostViolation<Int> = AtMostViolation(max = max, validationPath = path, reason = reason)

fun betweenViolation(
    min: Int = 1,
    max: Int = 5,
    path: ValidationPath = somePath,
    reason: String = "reason",
): BetweenViolation<Int> = BetweenViolation(min = min, max = max, validationPath = path, reason = reason)

fun distinctViolation(
    path: ValidationPath = somePath,
    reason: String = "reason",
): DistinctViolation = DistinctViolation(validationPath = path, reason = reason)

fun exactLengthViolation(
    expectedLength: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): ExactLengthViolation = ExactLengthViolation(expectedLength = expectedLength, validationPath = path, reason = reason)

fun exactSizeViolation(
    expectedSize: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): ExactSizeViolation = ExactSizeViolation(expectedSize = expectedSize, validationPath = path, reason = reason)

fun greaterThanViolation(
    minExclusive: Int = 1,
    path: ValidationPath = somePath,
    reason: String = "reason",
): GreaterThanViolation<Int> = GreaterThanViolation(minExclusive = minExclusive, validationPath = path, reason = reason)

fun lengthRangeViolation(
    minLength: Int = 1,
    maxLength: Int = 5,
    path: ValidationPath = somePath,
    reason: String = "reason",
): LengthRangeViolation = LengthRangeViolation(minLength = minLength, maxLength = maxLength, validationPath = path, reason = reason)

fun lessThanViolation(
    maxExclusive: Int = 1,
    path: ValidationPath = somePath,
    reason: String = "reason",
): LessThanViolation<Int> = LessThanViolation(maxExclusive = maxExclusive, validationPath = path, reason = reason)

fun maxLengthViolation(
    maxLength: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): MaxLengthViolation = MaxLengthViolation(maxLength = maxLength, validationPath = path, reason = reason)

fun maxSizeViolation(
    maxSize: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): MaxSizeViolation = MaxSizeViolation(maxSize = maxSize, validationPath = path, reason = reason)

fun minLengthViolation(
    minLength: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): MinLengthViolation = MinLengthViolation(minLength = minLength, validationPath = path, reason = reason)

fun minSizeViolation(
    minSize: Int = 3,
    path: ValidationPath = somePath,
    reason: String = "reason",
): MinSizeViolation = MinSizeViolation(minSize = minSize, validationPath = path, reason = reason)

fun notBlankViolation(
    path: ValidationPath = somePath,
    reason: String = "reason",
): NotBlankViolation = NotBlankViolation(validationPath = path, reason = reason)

fun notNullViolation(
    path: ValidationPath = somePath,
    reason: String = "reason",
): NotNullViolation = NotNullViolation(validationPath = path, reason = reason)

fun patternViolation(
    regex: Regex = Regex("a"),
    path: ValidationPath = somePath,
    reason: String = "reason",
): PatternViolation = PatternViolation(regex = regex, validationPath = path, reason = reason)

fun sizeBetweenViolation(
    minSize: Int = 1,
    maxSize: Int = 5,
    path: ValidationPath = somePath,
    reason: String = "reason",
): SizeBetweenViolation = SizeBetweenViolation(minSize = minSize, maxSize = maxSize, validationPath = path, reason = reason)
