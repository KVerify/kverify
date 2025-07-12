package io.github.kverify.rule.set

import io.github.kverify.core.model.NamedValue
import io.github.kverify.core.violation.Violation

internal typealias ValueViolationGenerator<T> = (T) -> Violation

internal typealias NamedValueViolationGenerator<T> = (NamedValue<T>) -> Violation
