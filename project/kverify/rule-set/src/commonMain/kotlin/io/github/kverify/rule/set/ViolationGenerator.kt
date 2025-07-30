package io.github.kverify.rule.set

import io.github.kverify.core.violation.Violation
import io.github.kverify.named.model.NamedValue

internal typealias ValueViolationGenerator<T> = (T) -> Violation

internal typealias NamedValueViolationGenerator<T> = (NamedValue<T>) -> Violation
