package io.github.kverify.core.violation

import io.github.kverify.core.model.ValidationPath

/**
 * A [Violation] with a [validationPath].
 */
public interface ViolationWithPath : Violation {
    public val validationPath: ValidationPath
}
