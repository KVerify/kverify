package io.github.kverify.core.violation

import io.github.kverify.core.context.ValidationPathElement

public interface PathAwareViolation : Violation {
    public val validationPath: List<ValidationPathElement>
}
