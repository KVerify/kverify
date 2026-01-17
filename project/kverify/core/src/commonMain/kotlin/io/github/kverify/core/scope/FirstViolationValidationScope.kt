package io.github.kverify.core.scope

import io.github.kverify.core.rule.Rule
import io.github.kverify.core.violation.Violation

public interface FirstViolationValidationScope : ValidationScope {
    public val firstViolation: Violation?

    override fun <T> T.verifyWith(rule: Rule<T>): T {
        val value = this
        val scope = this@FirstViolationValidationScope

        if (firstViolation != null) return value

        rule.execute(
            scope = scope,
            value = value,
        )

        return this
    }

    override fun <T> T.verifyWith(rules: Iterable<Rule<T>>): T {
        val value = this@verifyWith
        val scope = this@FirstViolationValidationScope

        if (firstViolation != null) return value

        for (rule in rules) {
            rule.execute(
                scope = scope,
                value = value,
            )

            if (firstViolation != null) break
        }

        return value
    }
}
