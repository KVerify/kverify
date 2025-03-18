package io.github.kverify.core.util

import io.github.kverify.core.context.ValidationContext
import io.github.kverify.core.model.Rule
import io.github.kverify.core.violation.Violation

data class StubRule<T>(
    val shouldFail: Boolean,
    val violation: Violation,
) : Rule<T> {
    override fun ValidationContext.runValidation(value: T) {
        if (shouldFail) onFailure(violation)
    }
}
