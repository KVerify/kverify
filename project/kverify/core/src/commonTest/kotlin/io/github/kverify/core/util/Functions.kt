package io.github.kverify.core.util

import io.github.kverify.core.violation.Violation
import io.github.kverify.core.violation.asViolationReason

fun violations(vararg names: String): List<Violation> = names.map { it.asViolationReason() }
