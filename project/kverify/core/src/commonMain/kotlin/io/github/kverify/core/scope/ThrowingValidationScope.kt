package io.github.kverify.core.scope

import io.github.kverify.core.violation.Violation

public interface ThrowingValidationScope : ValidationScope {
    override fun onFailure(violation: Violation): Nothing
}
