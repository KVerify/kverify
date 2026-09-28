package io.github.kverify.core.violation

import io.github.kverify.core.model.ValidationPathElement

public interface PathAwareViolation : Violation {
    public val validationPath: List<ValidationPathElement>
}
