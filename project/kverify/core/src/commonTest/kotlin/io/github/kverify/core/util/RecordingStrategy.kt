package io.github.kverify.core.util

import io.github.kverify.core.strategy.ValidationStrategy
import io.github.kverify.core.violation.Violation

class RecordingStrategy : ValidationStrategy {
    val violations = mutableListOf<Violation>()

    override fun failWith(violation: Violation) {
        violations.add(violation)
    }
}
