package io.github.kverify.core.violation

import io.github.kverify.core.model.ValidationPath

/**
 * A [Violation] with a [validationPath].
 */
public interface PathAwareViolation : Violation {
    public val validationPath: ValidationPath
}
