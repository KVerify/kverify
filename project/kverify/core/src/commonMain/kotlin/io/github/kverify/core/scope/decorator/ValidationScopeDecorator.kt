package io.github.kverify.core.scope.decorator

import io.github.kverify.core.scope.ValidationScope
import io.github.kverify.core.violation.Violation

public interface ValidationScopeDecorator<out T : ValidationScope> : ValidationScope {
    public val originalValidationScope: T

    override fun onFailure(violation: Violation): Unit = originalValidationScope.onFailure(violation)
}
