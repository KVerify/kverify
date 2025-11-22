package io.github.kverify.core.util

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

fun violations(vararg names: String): List<Violation> = names.map { it.asViolationReason() }

fun <T> List<Violation>.toFailingRules(): List<FailingRule<T>> = this.map { FailingRule(it) }

fun assertStored(
    expected: List<Violation>,
    violationStorage: List<Violation>,
) {
    assertEquals(expected.size, violationStorage.size)
    assertTrue(violationStorage.containsAll(expected))
}

fun assertStoredWithOrder(
    expected: List<Violation>,
    violationStorage: List<Violation>,
) {
    assertStored(expected, violationStorage)
    expected.zip(violationStorage) { exp, act -> assertSame(exp, act) }
}
