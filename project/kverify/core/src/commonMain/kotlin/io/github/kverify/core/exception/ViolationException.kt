package io.github.kverify.core.exception

import io.github.kverify.core.violation.Violation

/**
 * A [ValidationException] carrying exactly one [violation].
 *
 * Provides typed access to the single failure via [violation] without requiring a list lookup.
 *
 * @param violation The single violation that caused this exception.
 */
public class ViolationException(
    public val violation: Violation,
) : ValidationException(listOf(violation))
