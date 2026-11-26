package io.github.kverify.core

class RecordingStrategy : ValidationStrategy {
    val violations = mutableListOf<Violation>()

    override fun failWith(violation: Violation) {
        violations.add(violation)
    }
}
