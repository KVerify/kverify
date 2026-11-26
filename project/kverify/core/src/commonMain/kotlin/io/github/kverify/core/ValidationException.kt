package io.github.kverify.core

/**
 * An exception that indicates failed validation. Holds the [violations] that were found.
 */
public open class ValidationException(
    public val violations: List<Violation>,
) : RuntimeException("Validation failed with ${violations.size} violation(s)")

/**
 * A [ValidationException] with exactly one [violation].
 */
public class ViolationException(
    public val violation: Violation,
) : ValidationException(listOf(violation))
