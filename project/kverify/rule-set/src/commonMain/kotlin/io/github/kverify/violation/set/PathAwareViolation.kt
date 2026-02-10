package io.github.kverify.violation.set

import io.github.kverify.core.model.ValidationPath
import io.github.kverify.core.violation.Violation

internal interface PathAwareViolation : Violation {
    val validationPath: ValidationPath
}
