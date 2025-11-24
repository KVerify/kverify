package io.github.kverify.core.util

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun violations(vararg names: String): List<Violation> = names.map { it.asViolationReason() }

fun <T> List<Violation>.toFailingRules(): List<FailingRule<T>> = this.map { FailingRule(it) }

fun <T> assertContainsAll(
    expected: List<T>,
    actual: List<T>,
) {
    assertEquals(expected.size, actual.size)
    assertTrue(actual.containsAll(expected))
}

fun <T> assertContainsAllWithOrder(
    expected: List<T>,
    actual: List<T>,
) = assertEquals(expected, actual)
