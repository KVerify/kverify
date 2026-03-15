package io.github.kverify.violations

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.Violation

/**
 * A [Violation] that carries the path to the value that failed validation.
 */
public interface PathAwareViolation : Violation {
    /**
     * The path to the value that failed validation.
     *
     * An empty list means the violation was produced at the root scope, with no path context applied.
     */
    public val validationPath: ValidationPath
}
