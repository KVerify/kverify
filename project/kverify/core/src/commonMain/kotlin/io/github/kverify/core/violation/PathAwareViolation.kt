package io.github.kverify.core.violation

import io.github.kverify.core.model.ValidationPath

public interface PathAwareViolation : Violation {
    public val validationPath: ValidationPath
}
